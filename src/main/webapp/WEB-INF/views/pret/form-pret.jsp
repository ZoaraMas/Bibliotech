<%@ page import="java.util.List" %>
<div class="col-xl">
    <div class="card mb-6">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h5 class="mb-0">Pret</h5>
    </div>
    <div class="card-body">
        <form id="pretForm" method="post" action="<%= request.getContextPath() %>/pret/creer-date">
        <div class="mb-6">
            <label class="form-label" for="basic-default-fullname">Id Utilisateur</label>
            <input type="number" name="idUser" class="form-control" id="basic-default-fullname" placeholder="1">
        </div>
        <div class="mb-6">
            <label class="form-label" for="basic-default-company">Id Exemplaire</label>
            <input type="number" name="idExemplaire" class="form-control" id="basic-default-company" placeholder="1">
        </div>
        <div class="mb-4">
            <label for="defaultSelect" class="form-label">Type Pret</label>
            <select class="form-select" name="idTypePret" id="inputGroupSelect01" name="idTypePret" required>
                <option selected="">Choisir...</option>
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
        <div class="mb-4 row">
            <label for="html5-datetime-local-input"  class="col-md-2 col-form-label">Date et Heure</label>
            <div class="col-md-10">
                <input class="form-control" name="date" type="datetime-local" value="2021-06-18T12:30:00" id="html5-datetime-local-input">
                <%
                    java.time.LocalDateTime now = java.time.LocalDateTime.now();
                    String formattedNow = now.toString().substring(0,16); // "yyyy-MM-ddTHH:mm"
                %>
                <script>
                    document.getElementById('html5-datetime-local-input').value = '<%= formattedNow %>';
                </script>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Valider</button>
        </form>
        <div class="mb-6">
            <% 
            String error = (String) request.getAttribute("error");
            String succes = (String) request.getAttribute("succes");
            if(error != null) { %> 
                <div class="alert alert-danger" role="alert"><%= error %></div>
            <% } else if(succes != null) { %>
                <div class="alert alert-primary" role="alert"><%= succes %></div>
            <% } %>
        </div>
        
    </div>
    </div>
</div>