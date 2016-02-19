function y = leasqrfunc(x, p)
  global ratio_E_vs_E_filt;
  AHitr = load("-ascii", "HitranExtinktion5");
  grd = AHitr(:,1)';
  E = AHitr(:,2)';

  rangeL = min(grd);
  rangeH = max(grd);
  stp = (rangeH - rangeL)/(length(grd)-1);  % step size of HITRAN data points

  high_frequency = 4000;
  filt_width = 200; ## in cm-1, could be also round(rangeH - rangeL)
  x_fov = (rangeH + rangeL)/2;  ## center wavenumber position in cm-1
  offset = p(1);  # 0.001
  res = p(2);  ## ftir resolution in cm-1
  FOV = p(3);  %% aperture_size/2f(collimator) = aperture_radius/f(collimator) or half angle in mRad, 45 (Bruker gives full angle)
  amp = p(4);  ## proportional to partial pressure/mixing ratio of species [ppm]/[μmol/mol]

  % fit amplitude
  E = E * amp;
  area_E = trapz(grd,E);
  % apply filter
  filt_all = ftir_filter(high_frequency, filt_width, res, stp, FOV, x_fov);

  n_filt = length(filt_all);
  N = length(E);

  wn_filter_width = linspace(-filt_width/2+stp,filt_width/2,round(filt_width/stp));
  integral_filt_all = trapz(wn_filter_width, filt_all);
  E_filt = fftconv(filt_all, E, 2**nextpow2(n_filt))/integral_filt_all;
  length_filt = n_filt + N - 1;
  li = floor(n_filt/2);
  re = length_filt - li;
  area_E_filt = trapz(grd,E_filt(li:re));
  ratio_E_vs_E_filt = area_E/area_E_filt;  ## normalize the integral/area to the value without convolution
  E_filt = E_filt(li:re)*ratio_E_vs_E_filt + offset; ## do not apply offset before convolution/filtering!!!

  % adapt to vector length from measured data (spline)
  global baseline_corr
  if isnan(E_filt(1))  ## check if values in E_filt are numbers or NaN
    y = zeros(length(x),1); ## col vector
  else
    y = spline(grd, E_filt, x+p(6)) + baseline_corr*p(5);  ## p(6) shifts wavenumbers
  endif
endfunction
