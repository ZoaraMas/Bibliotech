-- Verifier si un membre est inscrit
SELECT * 
FROM inscription
WHERE id_user = 2
AND DATE_ADD(date_inscription, INTERVAL duree_mois MONTH) > NOW();
