function y = fnl(x,p1,p2)  ## x = range for S(Hitran) values=col vector;  p1/p2... polynomial start values
  y = x + p1*x.^2 + p2*x.^3;  ##  p1, p2 are 2nd and 3rd order adjustments to linearity (y=x)
endfunction
