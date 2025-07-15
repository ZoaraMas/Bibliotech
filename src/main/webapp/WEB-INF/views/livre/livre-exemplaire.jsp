<%@ page import="java.util.List" %>
<div class="col-md-6">
    <div class="card">
    <h5 class="card-header">Recherche les exemplaires d'un livre</h5>
    <div class="card-body">
        <div>
        <label for="defaultFormControlInput" class="form-label">ID</label>
        <input type="number" class="form-control" id="input" placeholder="1" aria-describedby="defaultFormControlHelp">
        <button type="submit" id="button" class="btn btn-primary">Valider</button>
        </div>
    </div>
    </div>
</div>
<div class="card">
    <h5 class="card-header">Dark Table head</h5>
    <div class="table-responsive text-nowrap">
            <table class="table">
            <thead class="table-dark">
                <tr>
                    <th>Reference</th>
                    <th>Disponibilite</th>
                    <th>Message</th>
                    <th>DateArrivee</th>
                </tr>
            </thead>
            <tbody class="table-border-bottom-0">
            <tr>
                <td>Ref</td>
                <td>
                    <ul class="list-unstyled m-0 avatar-group d-flex align-items-center">
                    <li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" class="avatar avatar-xs pull-up" aria-label="Lilian Fuller" data-bs-original-title="Lilian Fuller">
                        <img src="../assets/img/avatars/5.png" alt="Avatar" class="rounded-circle">
                    </li>
                    <li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" class="avatar avatar-xs pull-up" aria-label="Sophia Wilkerson" data-bs-original-title="Sophia Wilkerson">
                        <img src="../assets/img/avatars/6.png" alt="Avatar" class="rounded-circle">
                    </li>
                    <li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" class="avatar avatar-xs pull-up" aria-label="Christina Parker" data-bs-original-title="Christina Parker">
                        <img src="../assets/img/avatars/7.png" alt="Avatar" class="rounded-circle">
                    </li>
                    </ul>
                </td>
                <td><span class="badge bg-label-primary me-1">Active</span></td>
            </tr>
        </tbody>
        </table>
    </div>
    </div>


<script>
    function showAlert(message, type) {
        console.log(`Alert (${type}): ${message}`);
        // Example: You might update a div in your HTML here
        // const alertDiv = document.getElementById('my-alert-container');
        // if (alertDiv) {
        //     alertDiv.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
        // }
    }

    // Fonction ajax    
    function getLivreAvecExemplaires(idLivre, callback) {
        let res = null;
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8089/Bibliotech/livre/getLivre-avec-exemplaires?idLivre=1', true); // Configure la requête (méthode, URL)
        xhr.onreadystatechange = () => {
            if(xhr.readyState === 4) {
                if(xhr.status >= 200 && xhr.status < 300) {
                    try {
                        const response = xhr.responseText ? JSON.parse(xhr.responseText) : null;
                        res = response;
                        console.log(res);
                        callback(response);
                    }
                    catch(e) {
                        showAlert("Erreur: Format de reponse invalide du serveur", "danger");
                        callback(null);
                    }
                } else {
                    showAlert(`Errur serveur (${xhr.status}): ${xhr.statusText}`, "danger");
                    callback(null);
                }
            }
        };
        xhr.onerror = () => {
            showAlert("Erreur reseau: Impossible de se connecter au serveur", "danger");
        };
        xhr.send();
    }

    var button = document.getElementById('button');
    button.addEventListener('click', () => {
        var input = (document.getElementById('input')).value
        getLivreAvecExemplaires(input, (data) => {
            console.log(data);
        });
    })
</script>