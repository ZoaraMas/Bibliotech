<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Prêt - Bibliothèque</title>
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
                            <i class="fas fa-book me-2"></i>
                            Nouveau Prêt de Livre
                        </h4>
                    </div>
                    <div class="card-body">
                        <!-- Messages d'alerte -->
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
                        
                        <form id="pretForm" method="post" action="<%= request.getContextPath() %>/pret/creer-date">
                            <!-- ID de l'utilisateur (adhérent) -->
                            <div class="mb-3">
                                <label for="idUser" class="form-label">
                                    <i class="fas fa-user me-1"></i>
                                    ID Adhérent <span class="text-danger">*</span>
                                </label>
                                <input type="number" class="form-control" id="idUser" name="idUser" 
                                       placeholder="Entrez l'ID de l'adhérent" required>
                                <div class="form-text">Identifiant numérique de l'adhérent</div>
                            </div>

                            <!-- ID de l'exemplaire -->
                            <div class="mb-3">
                                <label for="idExemplaire" class="form-label">
                                    <i class="fas fa-book-open me-1"></i>
                                    ID Exemplaire <span class="text-danger">*</span>
                                </label>
                                <input type="number" class="form-control" id="idExemplaire" name="idExemplaire" 
                                       placeholder="Entrez l'ID de l'exemplaire" required>
                                <div class="form-text">Identifiant numérique de l'exemplaire à emprunter</div>
                            </div>

                            <!-- Type de prêt (dropdown depuis le model) -->
                            <div class="mb-3">
                                <label for="idTypePret" class="form-label">
                                    <i class="fas fa-tags me-1"></i>
                                    Type de Prêt <span class="text-danger">*</span>
                                </label>
                                <select class="form-select" id="idTypePret" name="idTypePret" required>
                                    <option value="">Sélectionnez un type de prêt</option>
                                    <%
                                        List<?> listeTypePret = (List<?>) request.getAttribute("listeTypePret");
                                        if (listeTypePret != null) {
                                            for (Object typePretObj : listeTypePret) {
                                                // Assumant que votre classe TypePret a des méthodes getId() et getLibelle()
                                                try {
                                                    Object id = typePretObj.getClass().getMethod("getId").invoke(typePretObj);
                                                    Object libelle = typePretObj.getClass().getMethod("getLibelle").invoke(typePretObj);
                                    %>
                                                    <option value="<%= id %>"><%= libelle %></option>
                                    <%
                                                } catch (Exception e) {
                                                    // En cas d'erreur, afficher l'objet tel quel
                                    %>
                                                    <option value="<%= typePretObj.toString() %>"><%= typePretObj.toString() %></option>
                                    <%
                                                }
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <!-- Informations sur le prêt (lecture seule) -->
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">
                                        <i class="fas fa-calendar me-1"></i>
                                        Date du prêt
                                    </label>
                                    <%
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        String dateAujourdhui = sdf.format(new Date());
                                    %>
                                    <input type="datetime-local" class="form-control" 
                                           value="<%= dateAujourdhui %>" name="date">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">
                                        <i class="fas fa-user-tie me-1"></i>
                                        Employé
                                    </label>
                                    <input type="text" class="form-control" 
                                           value="Employé connecté" readonly>
                                </div>
                            </div>

                            <!-- Boutons -->
                            <div class="d-flex justify-content-between">
                                <a href="<%= request.getContextPath() %>/pret" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>
                                    Retour
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save me-1"></i>
                                    Créer le Prêt
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
            $('#pretForm').on('submit', function(e) {
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
                        $('#pretForm')[0].reset();
                    },
                    error: function(xhr) {
                        let errorMessage = 'Erreur lors de la création du prêt';
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
                    showAlert('warning', 'Veuillez entrer un ID adhérent valide');
                    $('#idUser').focus();
                    isValid = false;
                }
                
                // Vérifier l'ID exemplaire
                const idExemplaire = $('#idExemplaire').val();
                if (!idExemplaire || idExemplaire <= 0) {
                    showAlert('warning', 'Veuillez entrer un ID exemplaire valide');
                    $('#idExemplaire').focus();
                    isValid = false;
                }
                
                // Vérifier le type de prêt
                if (!$('#idTypePret').val()) {
                    showAlert('warning', 'Veuillez sélectionner un type de prêt');
                    $('#idTypePret').focus();
                    isValid = false;
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
            $('#idUser, #idExemplaire').on('input', function() {
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

            // Validation pour le select
            $('#idTypePret').on('change', function() {
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