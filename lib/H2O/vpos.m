% vpos looks for index position of a given wavenumber vector, starting at lowest wavenumer
% lda is wavelength position where to look for
function [idx, wnr] = vpos (v, lda)
  i=1;
  if(lda > max(v))
    wnr = max(v);
    idx = length(v);
  elseif(v(1) > lda)
    wnr = v(i);
    idx = i;    
  else
   while(v(i) < lda)
    i++;
   endwhile
   if abs(v(i)-lda) < abs(v(i-1)-lda)
    wnr = v(i);
    idx = i;
   else
    wnr = v(i-1);
    idx = i-1;
   endif
  endif
endfunction
