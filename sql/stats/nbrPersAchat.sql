-- Pour chaque achat, le nombre de personne concernées

select ID_ACHAT, NOMBRE_DE_PERSONNE from nbr_personne_achat
where ID_ACHAT = 1
and CONCERNE = ?
