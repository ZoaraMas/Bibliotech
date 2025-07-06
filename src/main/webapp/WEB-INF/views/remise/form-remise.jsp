<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Remise de Livre - Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-success text-white">
                        <h4 class="mb-0">
                            <i class="fas fa-book-reader me-2"></i>
                            Remise de Livre
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
                        
                        <form id="remiseForm" method="post" action="<%= request.getContextPath() %>/remise-livre/creer">
                            <div class="mb-3">
                                <label for="idExemplaire" class="form-label">
                                    <i class="fas fa-book me-1"></i>
                                    ID Exemplaire <span class="text-danger">*</span>
                                </label>
                                <input type="number" class="form-control" id="idExemplaire" name="idExemplaire" 
                                       placeholder="Entrez l'ID de l'exemplaire à remettre" required>
                                <div class="form-text">Identifiant numérique de l'exemplaire à remettre.</div>
                            </div>

                            <div class="mb-3">
                                <label for="date" class="form-label">
                                    <i class="fas fa-calendar-alt me-1"></i>
                                    Date de Remise
                                </label>
                                <% 
                                    // Formate la date actuelle pour la valeur par défaut
                                    LocalDateTime defaultDate = LocalDateTime.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                                    String formattedDefaultDate = defaultDate.format(formatter);
                                %>
                                <input type="datetime-local" class="form-control" id="date" name="date" 
                                       value="<%= formattedDefaultDate %>">
                                <div class="form-text">Date et heure de remise (par défaut : maintenant).</div>
                            </div>

                            <div class="mb-3">
                                <label for="commentaire" class="form-label">
                                    <i class="fas fa-comment me-1"></i>
                                    Commentaire <span class="text-danger">*</span>
                                </label>
                                <textarea class="form-control" id="commentaire" name="commentaire" rows="3"
                                          placeholder="Ajoutez un commentaire sur cette remise (état du livre, observations, etc.)" required></textarea>
                                <div class="form-text">Informations sur l'état du livre ou observations particulières.</div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label class="form-label">
                                        <i class="fas fa-user-tie me-1"></i>
                                        Employé Effectuant la Remise
                                    </label>
                                    <input type="text" class="form-control" 
                                           value="Employé connecté (ID: <%= session.getAttribute("auth") != null ? session.getAttribute("auth") : "Non connecté" %>)" 
                                           readonly>
                                    <div class="form-text">Cet ID est géré automatiquement par la session.</div>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="<%= request.getContextPath() %>/pret" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>
                                    Retour
                                </a>
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-check me-1"></i>
                                    Effectuer la Remise
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
            $('#remiseForm').on('submit', function(e) {
                e.preventDefault();
                
                // Vérification de base
                if (!validateForm()) {
                    return;
                }
                
                // Désactiver le bouton pour éviter les doubles soumissions
                const submitBtn = $(this).find('button[type="submit"]');
                const originalText = submitBtn.html();
                submitBtn.prop('disabled', true).html('<i class="fas fa-spinner fa-spin me-1"></i>Traitement...');
                
                $.ajax({
                    url: $(this).attr('action'),
                    type: 'POST',
                    data: $(this).serialize(),
                    success: function(response) {
                        if (response.includes('succès')) {
                            showAlert('success', response);
                            // Réinitialiser le formulaire après succès
                            $('#remiseForm')[0].reset();
                            // Réinitialiser la date à maintenant
                            const now = new Date();
                            const year = now.getFullYear();
                            const month = String(now.getMonth() + 1).padStart(2, '0');
                            const day = String(now.getDate()).padStart(2, '0');
                            const hours = String(now.getHours()).padStart(2, '0');
                            const minutes = String(now.getMinutes()).padStart(2, '0');
                            $('#date').val(`${year}-${month}-${day}T${hours}:${minutes}`);
                        } else {
                            showAlert('danger', response);
                        }
                    },
                    error: function(xhr) {
                        let errorMessage = 'Erreur lors de la remise du livre';
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
                
                // Vérifier l'ID exemplaire
                const idExemplaire = $('#idExemplaire').val();
                if (!idExemplaire || idExemplaire <= 0) {
                    showAlert('warning', 'Veuillez entrer un ID exemplaire valide.');
                    $('#idExemplaire').focus();
                    isValid = false;
                }
                
                // Vérifier le commentaire
                const commentaire = $('#commentaire').val().trim();
                if (!commentaire || commentaire.length < 3) {
                    showAlert('warning', 'Veuillez entrer un commentaire d\'au moins 3 caractères.');
                    $('#commentaire').focus();
                    isValid = false;
                }

                // Vérifier la date (optionnel mais si renseignée, ne doit pas être dans le futur)
                const dateRemise = $('#date').val();
                if (dateRemise) {
                    const now = new Date();
                    const selectedDate = new Date(dateRemise);
                    if (selectedDate > now) {
                        showAlert('warning', 'La date de remise ne peut pas être dans le futur.');
                        $('#date').focus();
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

            // Validation en temps réel pour l'ID exemplaire
            $('#idExemplaire').on('input', function() {
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

            // Validation en temps réel pour le commentaire
            $('#commentaire').on('input', function() {
                const value = $(this).val().trim();
                if (value && value.length >= 3) {
                    $(this).removeClass('is-invalid').addClass('is-valid');
                } else {
                    $(this).removeClass('is-valid').addClass('is-invalid');
                }
            });

            // Validation en temps réel pour la date
            $('#date').on('change', function() {
                const value = $(this).val();
                if (value) {
                    const now = new Date();
                    const selectedDate = new Date(value);
                    if (selectedDate <= now) {
                        $(this).removeClass('is-invalid').addClass('is-valid');
                    } else {
                        $(this).removeClass('is-valid').addClass('is-invalid');
                    }
                } else {
                    $(this).removeClass('is-invalid is-valid');
                }
            });
        });
    </script>
</body>
</html>