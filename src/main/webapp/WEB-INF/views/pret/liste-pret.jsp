<%@ page import="java.util.List" %>
<%@ page import="com.Entite.PretParametreView" %>
<% 
    List<PretParametreView> listePret = (List<PretParametreView>) request.getAttribute("listePret");
    java.time.LocalDateTime now = java.time.LocalDateTime.now();
    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String nowFormatted = now.format(formatter);
%>

<div class="card">
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
    <h5 class="card-header">Liste des Prets</h5>
    <div class="table-responsive text-nowrap">
        <table class="table">
        <thead>
            <tr>
                <th>Id Pret</th>
                <th>Id Exemplaire</th>
                <th>Date Pret</th>
                <th>Remise</th>
                <th>Prolongement</th>
            </tr>
        </thead>
        <tbody class="table-border-bottom-0">
            <% for(int i = 0; i < listePret.size(); i ++) { 
                PretParametreView pret = listePret.get(i); %>
                <tr>
                    <td><%= pret.getId() %></td>
                    <td><%= pret.getIdExemplaire() %></td>
                    <td><%= pret.getDatePret() %></td>
                    <form method="post" action="<%= request.getContextPath() %>/remise-livre/creer">
                        <input type="hidden" name="idExemplaire" value="<%= pret.getIdExemplaire() %>">
                        <td>
                            <input type="text" name="commentaire" placeholder="Commentaire remise">

                            <input type="datetime-local" name="date" value="<%= nowFormatted %>" >

                            <button type="submit" class="btn btn-primary">Rendre</button>
                        </td>
                    </form>
                    <form method="post" action="<%= request.getContextPath() %>/prolongement/creer">
                        <input type="hidden" name="idPret" value="<%= pret.getId() %>">
                        <td>
                            <input type="text" name="commentaire" placeholder="Commentaire prolongement">

                            <input type="datetime-local" name="dateDemande" value="<%= nowFormatted %>" >

                            <button type="submit" class="btn btn-primary">Demande Prolongation</button>
                        </td>
                    </form>
                </tr>
            <% } %>
        </tbody>
        </table>
    </div>
</div>