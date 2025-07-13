<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.lang.reflect.Method" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle Réservation - Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">
                            <i class="fas fa-calendar-check me-2"></i>
                            Nouvelle Réservation de Livre
                        </h4>
                    </div>
                    <div class="card-body">
                        <div id="alertContainer">
                            <% 
                                String message = (String) request.getAttribute("message");
                                String error = (String) request.getAttribute("error");
                                if (message != null && !message.isEmpty()) {
                            %>
                                <div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <%= message %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            <% } %>
                            <% if (error != null && !error.isEmpty()) { %>
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <%= error %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                </div>
                            <% } %>
                        </div>
                        
                        <form id="reservationForm" method="post" action="<%= request.getContextPath() %>/reservation/creer">
                            <div class="mb-3">
                                <label for="idUser" class="form-label">
                                    <i class="fas fa-user me-1"></i>
                                    ID Adhérent <span class="text-danger">*</span>
                                </label>
                                <input type="number" class="form-control" id="idUser" name="idUser" 
                                       placeholder="Entrez l'ID de l'adhérent" required>
                                <div class="form-text">Identifiant numérique de l'adhérent.</div>
                            </div>

                            <div class="mb-3">
                                <label for="idExemplaire" class="form-label">
                                    <i class="fas fa-book-open me-1"></i>
                                    ID Exemplaire <span class="text-danger">*</span>
                                </label>
                                <input type="number" class="form-control" id="idExemplaire" name="idExemplaire" 
                                       placeholder="Entrez l'ID de l'exemplaire" required>
                                <div class="form-text">Identifiant numérique de l'exemplaire à réserver.</div>
                            </div>

                            <div class="mb-3">
                                <label for="idTypePret" class="form-label">
                                    <i class="fas fa-tags me-1"></i>
                                    Type de Prêt <span class="text-danger">*</span>
                                </label>
                                <%
                                    List<?> listeTypePret = (List<?>) request.getAttribute("listeTypePret");
                                    if (listeTypePret != null && !listeTypePret.isEmpty()) {
                                %>
                                    <select class="form-select" id="idTypePret" name="idTypePret" required>
                                        <option value="">Sélectionnez un type de prêt</option>
                                        <%
                                            for (Object typePretObj : listeTypePret) {
                                                try {
                                                    // Supposons que TypePret a des méthodes getId() et getLibelle()
                                                    Method getIdMethod = typePretObj.getClass().getMethod("getId");
                                                    Method getLibelleMethod = typePretObj.getClass().getMethod("getLibelle");
                                                    Object id = getIdMethod.invoke(typePretObj);
                                                    Object libelle = getLibelleMethod.invoke(typePretObj);
                                        %>
                                                    <option value="<%= id %>"><%= libelle %></option>
                                        <%
                                                } catch (Exception e) {
                                                    // En cas d'erreur de réflexion, afficher l'objet tel quel
                                        %>
                                                    <option value="<%= typePretObj.toString() %>"><%= typePretObj.toString() %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                <% } else { %>
                                    <input type="number" class="form-control" id="idTypePret" name="idTypePret" 
                                           placeholder="Entrez l'ID du type de prêt" required>
                                    <div class="form-text">Identifiant numérique du type de prêt (ex: 1 pour standard).</div>
                                <% } %>
                            </div>

                            <div class="mb-3">
                                <label for="dateCible" class="form-label">
                                    <i class="fas fa-calendar me-1"></i>
                                    Date Cible de Réservation <span class="text-danger">*</span>
                                </label>
                                <% 
                                    // Formate la date actuelle plus une semaine pour la valeur par défaut
                                    LocalDateTime defaultDate = LocalDateTime.now().plusWeeks(1);
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                                    String formattedDefaultDate = defaultDate.format(formatter);
                                %>
                                <input type="datetime-local" class="form-control" id="dateCible" name="dateCible" 
                                       value="<%= formattedDefaultDate %>" required>
                                <div class="form-text">Date et heure souhaitées pour la disponibilité du livre.</div>
                            </div>

                            <div class="mb-3">
                                <label for="commentaire" class="form-label">
                                    <i class="fas fa-comment me-1"></i>
                                    Commentaire
                                </label>
                                <textarea class="form-control" id="commentaire" name="commentaire" rows="3"
                                          placeholder="Ajoutez un commentaire sur cette réservation (raison, besoin spécifique, etc.)"></textarea>
                                <div class="form-text">Informations additionnelles pour la réservation.</div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label class="form-label">
                                        <i class="fas fa-user-tie me-1"></i>
                                        Employé Effectuant la Réservation
                                    </label>
                                    <input type="text" class="form-control" 
                                           value="Employé connecté (ID: <%= session.getAttribute("auth") != null ? session.getAttribute("auth") : "Non connecté" %>)" 
                                           readonly>
                                    <div class="form-text">Cet ID est géré automatiquement par la session.</div>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="<%= request.getContextPath() %>/reservation" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>
                                    Retour
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-1"></i>
                                    Créer la Réservation
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script>
        $(document).ready(function() {
            // Gestion du formulaire avec AJAX
            $('#reservationForm').on('submit', function(e) {
                e.preventDefault();
                
                // Vérification de base
                if (!validateForm()) {
                    return;
                }
                
                // Désactiver le bouton pour éviter les doubles soumissions
                const submitBtn = $(this).find('button[type="submit"]');
                const originalText = submitBtn.html();
                submitBtn.prop('disabled', true).html('<i class="fas fa-spinner fa-spin me-1"></i>Création...');
                
                $.ajax({
                    url: $(this).attr('action'),
                    type: 'POST',
                    data: $(this).serialize(),
                    success: function(response) {
                        showAlert('success', response);
                        // Réinitialiser le formulaire après succès
                        $('#reservationForm')[0].reset();
                        // Réinitialiser la date cible à sa valeur par défaut après reset
                        const now = new Date();
                        now.setDate(now.getDate() + 7); // Ajoute 7 jours pour la date cible par défaut
                        const year = now.getFullYear();
                        const month = String(now.getMonth() + 1).padStart(2, '0');
                        const day = String(now.getDate()).padStart(2, '0');
                        const hours = String(now.getHours()).padStart(2, '0');
                        const minutes = String(now.getMinutes()).padStart(2, '0');
                        $('#dateCible').val(`${year}-${month}-${day}T${hours}:${minutes}`);
                    },
                    error: function(xhr) {
                        let errorMessage = 'Erreur lors de la création de la demande de réservation';
                        if (xhr.responseText) {
                            errorMessage = xhr.responseText;
                        }
                        showAlert('danger', errorMessage);
                    },
                    complete: function() {
                        // Réactiver le bouton
                        submitBtn.prop('disabled', false).html(originalText);
                    }
                });
            });

            // Validation du formulaire
            function validateForm() {
                let isValid = true;
                
                // Vérifier l'ID utilisateur
                const idUser = $('#idUser').val();
                if (!idUser || idUser <= 0) {
                    showAlert('warning', 'Veuillez entrer un ID adhérent valide.');
                    $('#idUser').focus();
                    isValid = false;
                }
                
                // Vérifier l'ID exemplaire
                const idExemplaire = $('#idExemplaire').val();
                if (!idExemplaire || idExemplaire <= 0) {
                    showAlert('warning', 'Veuillez entrer un ID exemplaire valide.');
                    $('#idExemplaire').focus();
                    isValid = false;
                }
                
                // Vérifier le type de prêt
                const idTypePret = $('#idTypePret').val();
                if (!idTypePret || idTypePret <= 0) {
                    showAlert('warning', 'Veuillez sélectionner ou entrer un type de prêt valide.');
                    $('#idTypePret').focus();
                    isValid = false;
                }

                // Vérifier la date cible
                const dateCible = $('#dateCible').val();
                if (!dateCible) {
                    showAlert('warning', 'Veuillez spécifier une date cible pour la réservation.');
                    $('#dateCible').focus();
                    isValid = false;
                } else {
                    const now = new Date();
                    const selectedDate = new Date(dateCible);
                    if (selectedDate < now) {
                        showAlert('warning', 'La date cible ne peut pas être dans le passé.');
                        $('#dateCible').focus();
                        isValid = false;
                    }
                }
                
                return isValid;
            }

            // Fonction pour afficher les alertes
            function showAlert(type, message) {
                const alertHtml = `
                    <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                        <i class="fas fa-${getIconForType(type)} me-2"></i>
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                `;
                $('#alertContainer').html(alertHtml);
                
                // Scroll vers le haut pour voir l'alerte
                $('html, body').animate({
                    scrollTop: $('#alertContainer').offset().top - 20
                }, 300);
                
                // Auto-hide après 5 secondes pour les messages de succès
                if (type === 'success') {
                    setTimeout(function() {
                        $('.alert').alert('close');
                    }, 5000);
                }
            }

            // Fonction pour obtenir l'icône selon le type d'alerte
            function getIconForType(type) {
                switch(type) {
                    case 'success': return 'check-circle';
                    case 'danger': return 'exclamation-triangle';
                    case 'warning': return 'exclamation-circle';
                    default: return 'info-circle';
                }
            }

            // Validation en temps réel pour les champs numériques
            $('#idUser, #idExemplaire, #idTypePret').on('input', function() {
                const value = $(this).val();
                const field = $(this);
                
                // Supprimer les caractères non numériques
                if (value && !/^\d+$/.test(value)) {
                    field.val(value.replace(/\D/g, ''));
                }
                
                // Ajouter une classe de validation visuelle
                if (value && value > 0) {
                    field.removeClass('is-invalid').addClass('is-valid');
                } else {
                    field.removeClass('is-valid').addClass('is-invalid');
                }
            });

            // Validation en temps réel pour la date cible
            $('#dateCible').on('change', function() {
                const value = $(this).val();
                if (value) {
                    const now = new Date();
                    const selectedDate = new Date(value);
                    if (selectedDate >= now) {
                        $(this).removeClass('is-invalid').addClass('is-valid');
                    } else {
                        $(this).removeClass('is-valid').addClass('is-invalid');
                    }
                } else {
                    $(this).removeClass('is-valid').addClass('is-invalid');
                }
            });

            // Validation en temps réel pour le select (si utilisé)
            $('select#idTypePret').on('change', function() {
                const value = $(this).val();
                if (value) {
                    $(this).removeClass('is-invalid').addClass('is-valid');
                } else {
                    $(this).removeClass('is-valid').addClass('is-invalid');
                }
            });
        });
    </script>
</body>
</html>