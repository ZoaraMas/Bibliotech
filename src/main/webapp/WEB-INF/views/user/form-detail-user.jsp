<%@ page import="java.util.List" %>
<div class="col-md-6">
    <div class="card">
    <h5 class="card-header">Recherche Un adherent pour voir les details </h5>
    <div class="card-body">
        <div>
        <form action="<%= request.getContextPath() %>/user/info" method="get">
            <label for="defaultFormControlInput" class="form-label">ID</label>
            <input type="number" class="form-control" id="input" name="idUser" placeholder="1" aria-describedby="defaultFormControlHelp">
            <button type="submit" id="button" class="btn btn-primary">Valider</button>
        </form>
        </div>
    </div>
    </div>
</div>