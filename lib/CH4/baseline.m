function y = baseline(anfang, ende, step, wav, ab)  ## anfang, ende, step in cm-1
 [idx1, wnr1] = vpos(wav, anfang);   % 1st data point
 [idx2, wnr2] = vpos(wav, ende);   % last data point

 ab_min = min(ab(idx1:idx2));
 ab_off = ab(idx1:idx2) - ab_min;

 ab_base = []; wav_base = [];
 for i=anfang-step/2:step:ende+step/2
  [idxi1, wnri1] = vpos(wav, i);   % 1st data point
  [idxi2, wnri2] = vpos(wav, i+step);   % 2nd data point
  ab_base = [ab_base, min(ab(idxi1:idxi2))];
  wav_base = [wav_base, median(wav(idxi1:idxi2))];
 endfor

 y = spline(wav_base, ab_base, wav(idx1:idx2));  % col vector because of wav
endfunction
