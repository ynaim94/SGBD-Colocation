-- Liste des achats effectués pour une colocation et pour un mois donné (compil ok, test sur donnée pas fais)

select AC.INTITULE_ACHAT_COLOCATION AS ACHAT_COLOCATION
from ACHAT_COLOCATION AC, COLOCATION C
where AC.ID_COLOCATION=C.ID_COLOCATION
      and C.ID_COLOCATION= ?
      and EXTRACT( MONTH FROM AC.DATE_ACHAT_COLOCATION)= ?;

--format( 1,2,...,9,10,11,12)
