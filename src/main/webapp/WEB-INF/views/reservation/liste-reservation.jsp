<%@ page import="java.util.List" %>
<%@ page import="com.Entite.ReservationView" %>
<% 
    List<ReservationView> listeReservation = (List<ReservationView>) request.getAttribute("listeReservation");
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
    <h5 class="card-header">Liste des reservations</h5>
    <div class="table-responsive text-nowrap">
        <table class="table">
        <thead>
            <tr>
                <th>Id reservation</th>
                <th>Id Exemplaire</th>
                <th>Date Voulue</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody class="table-border-bottom-0">
            <% for(int i = 0; i < listeReservation.size(); i ++) { 
                ReservationView reservation = listeReservation.get(i); %>   
                <tr>
                    <form method="post" action="<%= request.getContextPath() %>/etat-reservation/creer">
                    <input type="hidden" name="idReservation" value="<%= reservation.getId() %>">
                    <td><%= reservation.getId() %></td>
                    <td><%= reservation.getIdExemplaire() %></td>
                    <td><%= reservation.getDateReservation() %></td>
                    <td>
                        <input type="text" name="commentaire" placeholder="Ajouter un commentaire">
                        <span>Valider: </span>
                        <input type="checkbox" name="confirmer" value="true">
                        <input type="hidden" name="confirmer" value="false">
                        <button type="submit" class="btn btn-primary">Valider</button>
                    </td>
                    </form>
                </tr>
            <% } %>
        </tbody>
        </table>
    </div>
</div>