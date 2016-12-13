@base
@donnees
-- Version minimal ( les 2 premieres requetes)
-- Version nominale ( les 3 premieres requetes)
-- Version avancée ( les 4 requetes)

-- La liste des colocation avec le nombre de leurs membre à une date donnée

select ID_COLOCATION, NOM_COLOCATION, count(ID_COLOCATION) as NOMBRE_MEMBRE
from (select ID_COLOCATION, NOM_COLOCATION, perso_contrat.ID_PERSONNE
     from  COLOCATION left join perso_contrat  using (ID_COLOCATION)
     where DATE_ENTREE < '16-AUG-2015'
     and (DATE_SORTIE is null or DATE_SORTIE > '16-AUG-2015')
     group by ID_COLOCATION, NOM_COLOCATION, perso_contrat.ID_PERSONNE)
group by (ID_COLOCATION, NOM_COLOCATION);

select ID_COLOCATION, NOM_COLOCATION, count(ID_COLOCATION) as NOMBRE_MEMBRE
from (select ID_COLOCATION, NOM_COLOCATION, perso_contrat.ID_PERSONNE
     from  COLOCATION left join perso_contrat  using (ID_COLOCATION)
     where DATE_ENTREE < '03-JAN-2015'
     and (DATE_SORTIE is null or DATE_SORTIE > '03-JAN-2015')
     group by ID_COLOCATION, NOM_COLOCATION, perso_contrat.ID_PERSONNE)
group by (ID_COLOCATION, NOM_COLOCATION);

-- Pour chaque achat, le nombre de personne concernées

select ID_ACHAT, NOMBRE_DE_PERSONNE from nbr_personne_achat;
where ID_ACHAT = 1
and CONCERNE = 'P';
select ID_ACHAT, NOMBRE_DE_PERSONNE from nbr_personne_achat
where ID_ACHAT = 2
and CONCERNE = 'P';
select ID_ACHAT, NOMBRE_DE_PERSONNE from nbr_personne_achat
where ID_ACHAT = 1
and CONCERNE = 'C';

-- Pour une personne donnée, la liste des débit et des crédits avec leur montant. une personne a un crédit si elle effectue un achat ou verse de l'argent à une autre personne ou à une cagnotte. Elle a un debit si elle recoit de l'argent ou si elle est concernée par un achat ( dans ce cas, le débit est égal au montant de l'achat divisé par le nombre de personne concernées)

select * from compte_total
where ID_PERSONNE = 1;

select * from compte_total
where ID_PERSONNE = 2;


-- Pour une colocation, la liste de ses membre avec leur solde.


select ID_ACHAT_COLOCATION, perso_contrat.ID_PERSONNE
     from ACHAT_COLOCATION, COLOCATION, perso_contrat
     where ACHAT_COLOCATION.ID_COLOCATION = COLOCATION.ID_COLOCATION
     and COLOCATION.ID_COLOCATION = perso_contrat.ID_COLOCATION
     group by ID_ACHAT_COLOCATION, perso_contrat.ID_PERSONNE;
