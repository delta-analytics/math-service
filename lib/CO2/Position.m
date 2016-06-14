% Position compares 2 vectors grd and v (v is within grd) and searches for the index position where v meets grd
% grd and v have same spacing stp
function [xl, xh] = Position(grd, v, stp)
  wnl = min(v);
  wnh = max(v);
  i = 1;
  while(abs(wnl-grd(i)) > 1000)  %speed search up
   i = i+1000/stp-10;
  endwhile
  while(abs(wnl-grd(i)) > 100)  %speed search up
   i = i+100/stp-10;
  endwhile
  while(abs(wnl-grd(i)) > 10)  %speed search up
   i = i+10/stp-10;
  endwhile
  while(abs(wnl-grd(i)) > 1)  %speed search up
   i = i+1/stp-1;
  endwhile
  while (grd(i) <= wnl)
   i++;
  endwhile 
  xl = i-1;
  do
   i++;
  until (grd(i) >= wnh)
  xh = i;
endfunction
