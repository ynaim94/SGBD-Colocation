-- Pour une personne donnée, la liste des débit et des crédits avec leur montant. une personne a un crédit si elle effectue un achat ou verse de l'argent à une autre personne ou à une cagnotte. Elle a un debit si elle recoit de l'argent ou si elle est concernée par un achat ( dans ce cas, le débit est égal au montant de l'achat divisé par le nombre de personne concernées)

select PERSONNE.ID_PERSONNE, NOM_PERSONNE, MONTANT_VERSEMENT as MONTANT, 'VERSEMENT' as NATURE, 'CREDIT' as TOTO
from PERSONNE, CONTRAT_MEMBRE, VERSEMENT
where PERSONNE.ID_PERSONNE = CONTRAT_MEMBRE.ID_PERSONNE
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = VERSEMENT.ID_CONTRAT_MEMBRE_PAYEUR
union
select PERSONNE.ID_PERSONNE, NOM_PERSONNE, MONTANT_ACHAT_PERSONNEL, 'ACHAT_PERSONNEL', 'CREDIT'
from PERSONNE, CONTRAT_MEMBRE, ACHAT_PERSONNEL
where PERSONNE.ID_PERSONNE = CONTRAT_MEMBRE.ID_PERSONNE
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = ACHAT_PERSONNEL.ID_CONTRAT_MEMBRE
union
select PERSONNE.ID_PERSONNE, NOM_PERSONNE, MONTANT_ACHAT_COLOCATION, 'ACHAT_COLOCATION', 'CREDIT'
from PERSONNE, CONTRAT_MEMBRE, ACHAT_COLOCATION
where PERSONNE.ID_PERSONNE = CONTRAT_MEMBRE.ID_PERSONNE
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = ACHAT_COLOCATION.ID_CONTRAT_MEMBRE
union
select PERSONNE.ID_PERSONNE, NOM_PERSONNE, MONTANT_ABONDEMENT, 'ABONDEMENT', 'CREDIT'
from PERSONNE, CONTRAT_MEMBRE, ABONDEMENT
where PERSONNE.ID_PERSONNE = CONTRAT_MEMBRE.ID_PERSONNE
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = ABONDEMENT.ID_CONTRAT_MEMBRE
union
select PERSONNE.ID_PERSONNE, NOM_PERSONNE, MONTANT_VERSEMENT, 'VERSEMENT', 'DEBIT'
from PERSONNE, CONTRAT_MEMBRE, VERSEMENT
where PERSONNE.ID_PERSONNE = CONTRAT_MEMBRE.ID_PERSONNE
and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = VERSEMENT.ID_CONTRAT_MEMBRE_RECEVEUR;

