<%@ page import="java.util.List" %>
<%@ page import="com.Entite.PretNombreDescView"%>
<%@ page import="com.Entite.UserCountPretDescView"%>
<%@ page import="com.Entite.User"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<% 
    List<PretNombreDescView> listeLivre = (List<PretNombreDescView>) request.getAttribute("listeLivre");
    List<UserCountPretDescView> listeUser = (List<UserCountPretDescView>) request.getAttribute("listeUser");
    HashMap<User, String> listeUserPenalise = (HashMap<User, String>) request.getAttribute("listeUserPenalise");
    java.time.LocalDateTime now = java.time.LocalDateTime.now();  
    java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    String nowFormatted = now.format(formatter);
%>

<div class="col-md-6 col-lg-4 col-xl-4 order-0 mb-6">
  <div class="card h-100">
    <div class="card-header d-flex justify-content-between">
      <div class="card-title mb-0">
        <h5 class="mb-1 me-2">Livre les plus pretes</h5>
        <p class="card-subtitle">42.82k Total Sales</p>
      </div>
      <div class="dropdown">
        <button class="btn text-muted p-0" type="button" id="orederStatistics" data-bs-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">

          <i class="bx bx-dots-vertical-rounded bx-lg"></i>
        </button>
        <div class="dropdown-menu dropdown-menu-end" aria-labelledby="orederStatistics">
          <a class="dropdown-item" href="javascript:void(0);">Select All</a>
          <a class="dropdown-item" href="javascript:void(0);">Refresh</a>
          <a class="dropdown-item" href="javascript:void(0);">Share</a>
        </div>
      </div>
    </div>
    <div class="card-body">
      <ul class="p-0 m-0">
      <% for(int i = 0; i < listeLivre.size(); i ++) {
        PretNombreDescView livre = listeLivre.get(i); %>
        <li class="d-flex align-items-center mb-5">
          <div class="avatar flex-shrink-0 me-3">
            <span class="avatar-initial rounded bg-label-primary"><i class="bx bx-mobile-alt"></i></span>
          </div>
          <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
            <div class="me-2">
              <h6 class="mb-0"><%= livre.getTitre() %></h6>
            </div>
            <div class="user-progress">
              <h6 class="mb-0"><%= livre.getNombrePret() %></h6>
            </div>
          </div>
        </li>
      <% } %>
      </ul>
    </div>
  </div>

  <div class="card h-100">
    <div class="card-header d-flex justify-content-between">
      <div class="card-title mb-0">
        <h5 class="mb-1 me-2">Les Utilisateurs qui on le plus pretes</h5>
        <p class="card-subtitle">42.82k Total Sales</p>
      </div>
      <div class="dropdown">
        <button class="btn text-muted p-0" type="button" id="orederStatistics" data-bs-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">
          <i class="bx bx-dots-vertical-rounded bx-lg"></i>
        </button>
        <div class="dropdown-menu dropdown-menu-end" aria-labelledby="orederStatistics">
          <a class="dropdown-item" href="javascript:void(0);">Select All</a>
          <a class="dropdown-item" href="javascript:void(0);">Refresh</a>
          <a class="dropdown-item" href="javascript:void(0);">Share</a>
        </div>
      </div>
    </div>
    <div class="card-body">
      <ul class="p-0 m-0">
      <% for(int i = 0; i < listeUser.size(); i ++) {
        UserCountPretDescView user = listeUser.get(i); %>
        <li class="d-flex align-items-center mb-5">
          <div class="avatar flex-shrink-0 me-3">
            <span class="avatar-initial rounded bg-label-primary"><i class="bx bx-mobile-alt"></i></span>
          </div>
          <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
            <div class="me-2">
              <h6 class="mb-0"><%= user.getNom() %></h6>
            </div>
            <div class="user-progress">
              <h6 class="mb-0"><%= user.getNombrePret() %></h6>
            </div>
          </div>
        </li>
      <% } %>
      </ul>
    </div>
  </div>
</div>
<div class="card overflow-hidden">
  <h5 class="card-header">Penalisations Actives</h5>
  <div class="table-responsive text-nowrap">
    <table class="table table-dark">
      <thead>
        <tr>
          <th>Id</th>
          <th>Nom</th>
          <th>Prenom</th>
          <th>Message</th>
        </tr>
      </thead>
      <tbody class="table-border-bottom-0">
        <% for (Map.Entry<User, String> entry : listeUserPenalise.entrySet()) {
          User user = entry.getKey();
          String message = entry.getValue();
        %>
          <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getNom() %></td>
            <td><%= user.getPrenom() %></td>
            <td><%= message %></td>
          </tr>
        <% } %>
      </tbody>
    </table>
  </div>
</div>