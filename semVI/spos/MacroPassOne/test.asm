MACRO
M1 &X,&Y,&A=,&B=BREG
MOVER &A,&X
ADD &A,='1'
MOVER &A,&Y
ADD &A,='5'
MEND

MACRO 
M2 &P,&Q,&U=CREG,&V=DREG
MOVER &U,&P
MOVER &V,&Q
ADD &U,='15'
ADD &V,='10'
MEND
