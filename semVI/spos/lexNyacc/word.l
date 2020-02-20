%{
#include<stdio.h>
int space_count = 0;
int enter_count = 0;
int char_count = 0;
%}


%%
\n {enter_count++;}
[ ] {space_count++;}
. {char_count++;}

%%

int yywrap(){
	return 1;
}

main(){
    extern FILE *yyin;
    yyin = fopen("input.txt","r");
	yylex();
    printf("Word count :%d\n",char_count);
    printf("Space count :%d\n",space_count);
    printf("Enter count :%d\n",enter_count);
}
