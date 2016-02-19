function [grd, SgmvTot, v0, gV, STot, STot2] = Hitran_function2( hitran_input, anfang, ende, stp, intensThres1, isotopo1, intensThres2, isotopo2, scalefactor, Temp, Pt, MW, Dd )
## Hitran_function2 = 2 isotopologues, display two isotopologues 
## Formeln aus Appl. Spectrosc. Vol 58(4), 2004, 468-473 und Hitran PC manual
## output: grd = grid in cm-1, stp interval, SgmvTot = theoretical slope from Hitran parameters, v0 = line center, gV = Voigt FWHM parameter
## input: hitran_input = data from one molecule, anfang,ende,stp = wavenumberrange and interval, intensThres(hold)i, isotopo(logue)i
## input: Temp, Pt, MW = temperature, pressure, molecular weight
## Achtung: csv hat die Molekülspalte, in2 hatte sie nicht!
## line shift due to pressure increase ν_c = ν_c0 + δ * P/P0 . v_c0=wavenumber at P0=1atm, δ=HITRAN parameter(10), P=pressure
## Population N in molecules/(cm3*ATM), N' in molecules/cm3
## S' = S * nL (296/T), nL Loschmidt number
## S' in units of 1/(cm²*ATM), S in units of cm⁻¹/(molecules*cm⁻²)
## Partition sum in parsum.dat, JavaHawks folder
## change of Pressure Pt und Temperature Temp go independent of mutual dependencies (universal gas low)
## all line profiles are normalized to area under the curve (Integral of function)
## Molecule in column, 	H2O: 2, 3, 4
##			CO2: 8, 9, 10
##			O3: 16, 17, 18
##			N2O: 34, 35, 36
##			CO: 39, 40, 41
##			CH4: 45, 46, 47
##			O2: 48, 49, 50
##			NO: 51, 52, 53
##			S2O: 54, 55, -
##			NO2: 56, -, -

	filename = hitran_input;  %%"02.csv";

	T0 = 296;  %% reference temperature
	hcKb100 = 6.626e-34 * 2.998e8 / 1.381e-23 * 100;  %% Planck * Lichtgeschwindigkeit * Boltzmann * (-1) = 1.4379 cm * K

	tm1 = time();
	C = csvread (filename);
	%fprintf("read hitran data in %1.2f sec\n", time()-tm1);

	% Anfang und Ende des Wellenzahlbereichs
	if nargin>1
	 anfang = anfang;
	else
	 anfang = 400;  %%min(C(:,3));
	endif
	if nargin>2
	 ende = ende;
	else
 	 ende = 4000;  %%max(C(:,3));
	endif
	if nargin>3
	 stp = stp;
	else
	 stp = 0.02;  %% calculation(convolution) stepsize
	endif
	if nargin>4
	 intensThres1 = intensThres1;
	else
	 intensThres1 = 1E-21;  %% Intensitiy threshold
	endif
	if nargin>5
	 isotopo1 = isotopo1;
	else
	 isotopo1 = 1;   %% Isotopologue according to natural abundance 1, 2, 3,...
	endif
	if nargin>6
	 intensThres2 = intensThres2;
	else
	 intensThres2 = 1E-23;  %% Intensitiy threshold
	endif
	if nargin>7
	 isotopo2 = isotopo2;
	else
	 isotopo2 = 2;   %% Isotopologue according to natural abundance 1, 2, 3,...
	endif
	if nargin>8
	 scalefactor = scalefactor;
	else
	 scalefactor = 1;   %% scaling factor to simulate relative difference between isotopologues
	endif
	if nargin>9
	 Temp = Temp;
	else
	 Temp = 296;  %% Temperature 20 °C
	endif
	if nargin>10
	 Pt = Pt;
	else
	 Pt = 1;  %% total Pressure in ATM units
	endif
	if nargin>11
	 MW = MW;
	else
	 MW = 44;  %% molecular weight CO2
	endif
	if nargin>12
	 Dd = Dd;
	else
	 Dd = 2;  %% max width of one line, Dd = edge/wing
	endif
	%N = length(C(:,1));
	[idx1, wnr1] = vpos(C(:,3), anfang);   % 1st data point
	[idx2, wnr2] = vpos(C(:,3), ende);   % last data point
	%fprintf("selected lines %d\n", length(C(idx1:idx2, 1)));
        %% define a grid
	%Dd = 1.0; %% define the edge extended beyond the spectrum region; Dd is also used for the wings of each line
        rangeL =  -Dd + wnr1;  %% spectrum region: First line minus Dd cm-1
        rangeH =   Dd + wnr2;  %% Last line position + Dd cm-1
        grd=[rangeL:stp:rangeH];  %% define the grid

	PT = load("ParSum_ohne_erste_Zeile.dat");  ## starting at 70K = 1, 71K = 2, 296K -> 296 - 69 = 227 -row number-
	PSM = [[2,8,16,34,39,45,48,51,54,56];[3,9,17,35,40,46,49,52,55,56];[4,10,18,36,41,47,50,53,55,56]];  %% PartitionSum-Matrix row=isotopo, col=melecule, molecule 9=SO2 only 2 cols, molecule 10=N2O only one col
	pos_PSM = strread(substr(filename, 1, 2), "%u");  %% filename is 01.csv, 02.csv,... -> H2O, CO2, O3,... 

        SgmvTot = 0; SgmvTot1 = 0; SgmvTot2 = 0; STot = 0; STot2 = 0;
	S_hitr1 = []; S_hitr2 = []; S_hitr_max = 0;
	v_hitr1 = []; v_hitr2 = []; v_hitr = []; v_hitr_max = 0;
	ft_ = []; QrT_ratio_ = []; v0 = [];

	tm2 = time();
        k = 0; gV = 0;
	v_shift = [];
        for i=idx1:idx2  %% calculate the line shape for each peak k
	    
	  if((C(i,4) > intensThres1 && C(i,2) == isotopo1) || (C(i,4) > intensThres2 && C(i,2) == isotopo2))
	  
	    k++;
            v0(k) = C(i,3);
	    #STot = STot + C(i,4);  ## for natural isotope abundance only
	    v_shift = [v_shift,	C(i,10) * Pt];  ## C(i,10) air pressure induced line shift  for total pressure Pt in ATM units
            v = [v0(k)-Dd+stp:stp:v0(k)+Dd-stp];  ## range from line center to the wings
	    if (C(i,2) == isotopo2)
	      S = C(i,4)*scalefactor;   %% scaling factor to simulate different delta  (natural abundance: delta=0 for CO2 pdb=0.0112375)
	      STot2 = STot2 + C(i,4)*scalefactor;
      	      v_hitr2 = [v_hitr2, v0(k)];
	      S_hitr2 = [S_hitr2, S/(pi*C(i,6))];
            else
	      S = C(i,4);
	      STot = STot + C(i,4);
	      if S/(pi*C(i,6)) > max(S_hitr1)
		S_hitr_max = S/pi * C(i,6)./((v-v0(k)).^2 + C(i,6)^2);
		v_hitr_max = v;
	      endif
    	      v_hitr1 = [v_hitr1, v0(k)];
	      S_hitr1 = [S_hitr1, S/(pi*C(i,6))];
	    endif
 	    v_hitr = [v_hitr, v0(k)];
            %% gD and gL are HWHM, so multiply by 2 to make FWHM for Whiting and Olivero and Longbothum formula
	    gD = 2 * 3.5812 * 1E-7 * v0(k) * sqrt(Temp/MW);  ## gD = v0/c * sqrt(2ln(2)*RT/MW)
	    gL = 2 * (T0/Temp)^C(i,9) * C(i,6) * Pt;  %% Pressure broadening from Hitran data base (air broadening), self broadening neglegted
	    #Pp = 400e-6;  ## mixing ratio ppm or μmol/mol
	    #gL = 2 * (T0/Temp)^C(i,9) * (C(i,7) * Pp + C(i,6) * (Pt - Pp));  %% with self broadening, Pp partial pressure

	    % temperature dependance
	    T1 = T0; T2 = Temp;
	    QrT1 = PT(T0-69,PSM(C(i,2), pos_PSM));   %% Zustandsumme für z.B. 626 CO2 bei 296K,  PSM(isotopo = row, molecule = col)
	    QrT2 = PT(Temp-69,PSM(C(i,2), pos_PSM));   %% Zustandsumme für z. B. 626 CO2 bei 313K
	    #lsE = C(i,8);  %% lower state energy E'' and v in cm-1 -> multiply hc/Kb by 100
	    #ft = (1-exp(-hcKb100*v0(k)/T2))/(1-exp(-hcKb100*v0(k)/T1)) * exp(-hcKb100*lsE*(1/T2-1/T1)) * QrT2/QrT1 * T1/T2;  %% see Appl. Spectrosc. paper above, formula is for S' in units of 1/(cm²*ATM)
	    QrT_ratio = QrT1/QrT2;
	    QrT_ratio_ = [QrT_ratio_, QrT_ratio];
	    #ft = QrT1/QrT2 * exp(1.4379 * v0(k) * (T2 - T1) / (T2 * T1));  %% Hitran PC- manual p. 130, with constant hc/Kb = 1.439, S in units of cm⁻¹/(molecules*cm⁻²)
	    ft = 1; ## check the effect of ft
	    ft_ = [ft_, ft];
	    gV(k) = (0.5346*gL + sqrt(0.2166*gL^2 + gD^2)); %% Voigt profile full width FWHM  from gL and gD (Olivero and Longbothum, 1977)
	    x = gL/gV(k);  ## FWHM/FWHM
	    y = abs(v-v0(k))/gV(k);  ## 1/FWHM

	    %% one spectral line having resolution defined in stp, width determined in y respective v, FWHM -> HWHM adapted
	    %% formulas from Appl. Spectrosc. Vol 58(4), 2004: use gD and gL from above without factor 2 or gV(k)/2 and y*2
#	    Sgmv0 = 1/(2*gV(k)*(1.065 + 0.447*x + 0.058*x^2));  ## FWHM
#            Sgmv = ft * S * Sgmv0 * ((1-x)*exp(-0.693.*y.^2) + (x./(1+y.^2)) + ...
#		0.016*(1-x)*x*(exp(-0.0841.*y.^2.25) - 1./(1 + 0.021.*y.^2.25)));  %% Whiting, 1968, converted to HWHM, use gV(k)/2!!!
	    Sgmv0 = 1/(gV(k)*(1.065 + 0.447*x + 0.058*x^2));  ## FWHM
	    Sgmv = ft * S * Sgmv0 * ((1-x)*exp(-2.772*y.^2) + (x./(1+4*y.^2)) + ...
		0.016*(1-x)*x*(exp(-0.40.*y.^2.25) - 10./(10 + y.^2.25)));  ## Hitran PC-manual with FWHM widths
#	    Sgmv = ft * S/pi * (gL/2)./((v-v0(k)).^2 + (gL/2)^2);  ## pure Lorentz HWHM
#	    Sgmv = ft * S/(gD/2) * sqrt(log(2)/pi) * exp(-log(2)*(v-v0(k)).^2/(gD/2)^2);  ## pure Gauss HWHM

	    lenv = length(v);
	    [xl, xh] = Position(grd, v, stp);
	    if(xh-xl != lenv)  %% sometimes length of Sgmv and v do not match by 1 unit
	    	xl--;
	    endif
	    LN = zeros(1,xl-1);
	    HN = zeros(1,length(grd)-(xh-1));
 
	    if (C(i,2) == isotopo1)
		SgmvTot1 = SgmvTot1 + [LN, Sgmv, HN];
	    else
		SgmvTot2 = SgmvTot2 + [LN, Sgmv, HN];
	    endif
	  endif
        endfor  %% calculate the line shape for each peak k
	if length(SgmvTot1) == 1  ## SgmvTot1 = 0
	  integral_SgmvTot1 = 1; STot = 1;
	  SgmvTot1 = ones(1,length(grd))*1e-30; v0 = grd; gV = ones(1,length(grd))*1e-3;
	else
	  integral_SgmvTot1 = trapz(grd,SgmvTot1);
	endif
	if length(SgmvTot2) == 1  ## SgmvTot2 = 0
	  integral_SgmvTot2 = 1; STot2 = 1;
	  SgmvTot2 = ones(1,length(grd))*1e-30;
	else
	  integral_SgmvTot2 = trapz(grd,SgmvTot2);
	endif
        SgmvTot = SgmvTot1 + SgmvTot2;
	SgmvTot1 = SgmvTot1 * STot/integral_SgmvTot1;  ## normalize to total sum of Hitran line strengths, STP standard temperature and presssure
	SgmvTot2 = SgmvTot2 * STot2/integral_SgmvTot2;  ## normalize to total sum of Hitran line strengths, STP standard temperature and presssure
	STot = STot + STot2;
	#SgmvTot = SgmvTot * STot/integral_SgmvTot;  ## normalize to total sum of Hitran line strengths, STP standard temperature and presssure
	%fprintf("calculate hitran spectra in %1.2f sec\n", time()-tm2);
endfunction
