-- Liste des achats effectués pour une colocation et pour un mois donné

select AC.INTITULE_ACHAT_COLOCATION AS ACHAT_COLOCATION, AC.MONTANT_ACHAT_COLOCATION as PRIX
from ACHAT_COLOCATION AC, COLOCATION C
where AC.ID_COLOCATION=C.ID_COLOCATION
      and C.ID_COLOCATION=??
      and EXTRACT( MONTH FROM AC.DATE_ACHAT_COLOCATION)=??; 

--format pour les mois( 1,2,...,9,10,11,12)