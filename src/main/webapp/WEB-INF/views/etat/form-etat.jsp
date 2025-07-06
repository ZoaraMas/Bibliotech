<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Validation de Réservation - Bibliothèque</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-warning text-dark">
                        <h4 class="mb-0">
                            <i class="fas fa-tasks me-2"></i>
                            Validation de Réservation
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
                        
                        <form id="etatForm" method="post" action="<%= request.getContextPath() %>/etat-reservation/creer">
                            <div class="mb-3">
                                <label for="idReservation" class="form-label">
                                    <i class="fas fa-bookmark me-1"></i>
                                    ID Réservation <span class="text-danger">*</span>
                                </label>
                                <input type="number" class="form-control" id="idReservation" name="idReservation" 
                                       placeholder="Entrez l'ID de la réservation à traiter" required>
                                <div class="form-text">Identifiant numérique de la réservation à valider ou refuser.</div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">
                                    <i class="fas fa-decision me-1"></i>
                                    Décision <span class="text-danger">*</span>
                                </label>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="confirmer" id="confirmerOui" value="true" required>
                                    <label class="form-check-label text-success fw-bold" for="confirmerOui">
                                        <i class="fas fa-check-circle me-1"></i>
                                        Valider la réservation
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="confirmer" id="confirmerNon" value="false" required>
                                    <label class="form-check-label text-danger fw-bold" for="confirmerNon">
                                        <i class="fas fa-times-circle me-1"></i>
                                        Refuser la réservation
                                    </label>
                                </div>
                                <div class="form-text">Choisissez si vous voulez valider ou refuser cette réservation.</div>
                            </div>

                            <div class="mb-3">
                                <label for="commentaire" class="form-label">
                                    <i class="fas fa-comment me-1"></i>
                                    Commentaire
                                </label>
                                <textarea class="form-control" id="commentaire" name="commentaire" rows="3"
                                          placeholder="Ajoutez un commentaire sur cette décision (motif de refus, observations, etc.)"></textarea>
                                <div class="form-text">Informations additionnelles sur votre décision (optionnel).</div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-12">
                                    <label class="form-label">
                                        <i class="fas fa-user-tie me-1"></i>
                                        Employé Validant
                                    </label>
                                    <input type="text" class="form-control" 
                                           value="Employé connecté (ID: <%= session.getAttribute("auth") != null ? session.getAttribute("auth") : "Non connecté" %>)" 
                                           readonly>
                                    <div class="form-text">Cet ID est géré automatiquement par la session.</div>
                                </div>
                            </div>

                            <div class="alert alert-info" role="alert">
                                <i class="fas fa-info-circle me-2"></i>
                                <strong>Information :</strong> Cette action est définitive et ne peut pas être annulée.
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="<%= request.getContextPath() %>/reservation" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>
                                    Retour
                                </a>
                                <button type="submit" class="btn btn-warning text-dark">
                                    <i class="fas fa-gavel me-1"></i>
                                    Valider la Décision
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
            $('#etatForm').on('submit', function(e) {
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
                        if (response.includes('VALIDEE') || response.includes('REFUSEE')) {
                            // Déterminer le type d'alerte selon la réponse
                            const alertType = response.includes('VALIDEE') ? 'success' : 'warning';
                            showAlert(alertType, response);
                            // Réinitialiser le formulaire après succès
                            $('#etatForm')[0].reset();
                        } else {
                            showAlert('danger', response);
                        }
                    },
                    error: function(xhr) {
                        let errorMessage = 'Erreur lors du traitement de la réservation';
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
                
                // Vérifier l'ID réservation
                const idReservation = $('#idReservation').val();
                if (!idReservation || idReservation <= 0) {
                    showAlert('warning', 'Veuillez entrer un ID de réservation valide.');
                    $('#idReservation').focus();
                    isValid = false;
                }
                
                // Vérifier qu'une décision a été sélectionnée
                const confirmer = $('input[name="confirmer"]:checked').val();
                if (!confirmer) {
                    showAlert('warning', 'Veuillez sélectionner une décision (valider ou refuser).');
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
                if (type === 'success' || type === 'warning') {
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

            // Validation en temps réel pour l'ID réservation
            $('#idReservation').on('input', function() {
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

            // Validation en temps réel pour les boutons radio
            $('input[name="confirmer"]').on('change', function() {
                const value = $(this).val();
                if (value) {
                    $('input[name="confirmer"]').removeClass('is-invalid');
                    $(this).addClass('is-valid');
                    
                    // Changer la couleur du bouton selon la décision
                    const submitBtn = $('button[type="submit"]');
                    if (value === 'true') {
                        submitBtn.removeClass('btn-warning btn-danger').addClass('btn-success');
                        submitBtn.html('<i class="fas fa-check me-1"></i>Valider la Réservation');
                    } else {
                        submitBtn.removeClass('btn-warning btn-success').addClass('btn-danger');
                        submitBtn.html('<i class="fas fa-times me-1"></i>Refuser la Réservation');
                    }
                }
            });

            // Validation en temps réel pour le commentaire (optionnel)
            $('#commentaire').on('input', function() {
                const value = $(this).val().trim();
                if (value && value.length >= 3) {
                    $(this).removeClass('is-invalid').addClass('is-valid');
                } else if (value && value.length < 3) {
                    $(this).removeClass('is-valid').addClass('is-invalid');
                } else {
                    $(this).removeClass('is-valid is-invalid');
                }
            });

            // Confirmation avant soumission pour les refus
            $('#etatForm').on('submit', function(e) {
                const confirmer = $('input[name="confirmer"]:checked').val();
                if (confirmer === 'false') {
                    const confirm = window.confirm('Êtes-vous sûr de vouloir REFUSER cette réservation ? Cette action est définitive.');
                    if (!confirm) {
                        e.preventDefault();
                        return false;
                    }
                }
            });
        });
    </script>
</body>
</html>