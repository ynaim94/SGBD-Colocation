-- La liste des colocation avec le nombre de leurs membre à une date donnée


select ID_COLOCATION, NOM_COLOCATION, count(ID_COLOCATION) as NOMBRE_MEMBRE
from (select ID_COLOCATION, NOM_COLOCATION, perso_contrat.ID_PERSONNE
     from  COLOCATION left join perso_contrat  using (ID_COLOCATION)
     where DATE_ENTREE < ?
     and (DATE_SORTIE is null or DATE_SORTIE > ?)
     group by ID_COLOCATION, NOM_COLOCATION, perso_contrat.ID_PERSONNE)
group by (ID_COLOCATION, NOM_COLOCATION);
