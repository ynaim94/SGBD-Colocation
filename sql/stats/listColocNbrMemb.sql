-- La liste des colocation avec le nombre de leurs membre à une date donnée

select ID_COLOCATION, COLOCATION.NOM_COLOCATION, count(ID_CONTRAT_MEMBRE)
from COLOCATION left join CONTRAT_MEMBRE using (ID_COLOCATION)
group by (ID_COLOCATION, COLOCATION.NOM_COLOCATION);

