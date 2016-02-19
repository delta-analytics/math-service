%% Filter function to produce ILS function to apply to theoretical HITRAN data
%% Contribution from Hardware (finite interferometer path and divergence of beam =FOV) and software (=Apodization)
%% input: high_frequency, filt_width, res, stp, FOV, w_fov
%% high_frequency = highest frequency detected in complete spectrum
%% filt_width = filter width in cm-1
%% res = resolution in cm-1 (inverse interferometer path length in cm)
%% stp = step size of Hitran data in cm-1
%% FOV = field of view (divergence of beam half angle) in mRad
%% w_fov = center wavelength in cm-1 (it should be set for each line individually)
%% output: y values of filter function, with same step size as Hitran data

function y = ftir_filter(high_frequency, filt_width, res, stp, FOV, w_fov)
  %% finite interferometer path: sinc function has argument pi*x, interval -100 ... 100 ensures enough side lobes
  #res = 1;  ## resolution in cm-1
  dps = round(1/stp);  ## data points per cm-1 from Hitran
  filt_width = round(filt_width);  ## zero crossings in sinc, units cm-1
  wn_filter_range = linspace(-filt_width/2+stp,filt_width/2,filt_width*dps);  ## define grid in wavenumber space, zero center
  filter_range = length(wn_filter_range);  ## multiple of 2
  filt_sinc = sinc(wn_filter_range*2/res);  ## check sinc arguments with FTIRFolding.m
  integral_filt_sinc = trapz(wn_filter_range, filt_sinc);
  %% divergent beam
  #FOV = 22.5*1e-3;  %% aperture_size/2f(collimator) = aperture_radius/f(collimator) or half angle in mRad 45 (Bruker gives full angle)
  #w_fov = 2350
  rect_wd = w_fov * (FOV*FOV)/2;  %% boxcar width due to diverget beam FOV in cm-1, and maximum recorded wavenumber
  n_rect_wd = round(dps * rect_wd);  %% data points per cm-1
  filt_rect = [zeros(1,filter_range/2-n_rect_wd), ones(1,n_rect_wd), zeros(1,filter_range/2)];  %% 2nd filter to simulate FTIR spectrometer ILS
  integral_filt_rect = trapz(wn_filter_range, filt_rect);
  %% apodization
  #high_frequency = 4000;  %% highest frequency in cm-1, high_folding_limit
  wn = linspace(0+stp, high_frequency, high_frequency*dps);
  zerofil = dps;   ## be greater than 16
  filter_n = length(wn)/zerofil;   ## make filter_n to 1 point per cm-1
  window_function = blackman(round(filter_n*2/res));
  #window_function = nortonbeer(round(filter_n*2/res),'strong');
  ft_windowing = fftshift(abs(fft(window_function,filter_n*zerofil)))'/round(filter_n*2/res);  ## check FTIRFolding.m
  ft_windowing1 = ft_windowing(length(wn)/2-filter_range/2:length(wn)/2+filter_range/2-1);
  integral_ft_windowing1 = trapz(wn_filter_range, ft_windowing1);
  fft_power = nextpow2(filter_range);
  fftconv_length = 2**fft_power;
  filt_sinc_rect = fftconv(filt_sinc, filt_rect, fftconv_length)/integral_filt_sinc;
  length_filt = 2*filter_range - 1;
  li = floor(filter_range/2);
  re = length_filt - li;  ## 
  filt_sinc_rect = filt_sinc_rect(li:re);
  filt_all = fftconv(ft_windowing1, filt_sinc_rect, fftconv_length)/integral_ft_windowing1;
      if false
	figure();
	hold on
	title("filters");
	plot(wn_filter_range, filt_sinc, strcat("-r;sinc(",num2str(res),"cm-1);"), wn_filter_range, filt_rect, strcat("-g;FOV=",num2str(rect_wd),"cm-1;"), wn_filter_range, ft_windowing1, "-b;apodization;", wn_filter_range, filt_all(li:re)/max(filt_all), "-k;convolution of all;");
	grid('minor')
	hold off
      end
  y = filt_all(li:re);
endfunction
