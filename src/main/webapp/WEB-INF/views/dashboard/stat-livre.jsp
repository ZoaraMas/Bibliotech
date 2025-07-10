<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Statistiques des Livres les Plus Prêtés</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            text-align: center;
        }
        h1 {
            color: #333;
        }
        #pieChartContainer {
            width: 80%; /* Adjust as needed */
            max-width: 600px; /* Max width for better display */
            margin: 40px auto; /* Center the container */
            border: 1px solid #ddd;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            background-color: #fff;
        }
        .loading-message, .error-message {
            margin-top: 20px;
            font-size: 1.1em;
            color: #555;
        }
        .error-message {
            color: red;
        }
    </style>
</head>
<body>
    <div id="pieChartContainer">
        <canvas id="livrePieChart"></canvas>
        <div class="loading-message" id="loadingMessage">Chargement des données du graphique...</div>
        <div class="error-message" id="errorMessage" style="display: none;"></div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const loadingMessage = document.getElementById('loadingMessage');
            const errorMessage = document.getElementById('errorMessage');
            const pieChartContainer = document.getElementById('pieChartContainer');

            // Fetch data from the REST API
            fetch('/Bibliotech/dashboard/livre-les-plus-pretes')
                .then(response => {
                    if (!response.ok) {
                        // If the server response is not OK (e.g., 404, 500)
                        throw new Error(`Erreur HTTP: ${response.status} - ${response.statusText}`);
                    }
                    return response.json(); // Parse the JSON response
                })
                .then(data => {
                    // Hide loading message
                    loadingMessage.style.display = 'none';

                    if (data && data.length > 0) {
                        const labels = data.map(item => item.titre);
                        const dataPoints = data.map(item => item.nombre);

                        const ctx = document.getElementById('livrePieChart').getContext('2d');

                        new Chart(ctx, {
                            type: 'pie', // Type of chart
                            data: {
                                labels: labels, // Book titles
                                datasets: [{
                                    label: 'Nombre de Prêts',
                                    data: dataPoints, // Number of loans for each book
                                    backgroundColor: [ // Define colors for your pie slices
                                        'rgba(255, 99, 132, 0.8)',
                                        'rgba(54, 162, 235, 0.8)',
                                        'rgba(255, 206, 86, 0.8)',
                                        'rgba(75, 192, 192, 0.8)',
                                        'rgba(153, 102, 255, 0.8)',
                                        'rgba(255, 159, 64, 0.8)',
                                        'rgba(199, 199, 199, 0.8)',
                                        'rgba(83, 102, 255, 0.8)',
                                        'rgba(255, 99, 71, 0.8)',
                                        'rgba(60, 179, 113, 0.8)'
                                        // Add more colors if you expect many books
                                    ],
                                    borderColor: [
                                        'rgba(255, 99, 132, 1)',
                                        'rgba(54, 162, 235, 1)',
                                        'rgba(255, 206, 86, 1)',
                                        'rgba(75, 192, 192, 1)',
                                        'rgba(153, 102, 255, 1)',
                                        'rgba(255, 159, 64, 1)',
                                        'rgba(199, 199, 199, 1)',
                                        'rgba(83, 102, 255, 1)',
                                        'rgba(255, 99, 71, 1)',
                                        'rgba(60, 179, 113, 1)'
                                    ],
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                plugins: {
                                    title: {
                                        display: true,
                                        text: 'Distribution des Prêts par Livre'
                                    },
                                    tooltip: {
                                        callbacks: {
                                            label: function(context) {
                                                let label = context.label || '';
                                                if (label) {
                                                    label += ': ';
                                                }
                                                // Display the value and percentage
                                                const total = context.dataset.data.reduce((sum, val) => sum + val, 0);
                                                const currentValue = context.parsed;
                                                const percentage = parseFloat((currentValue / total * 100).toFixed(1));
                                                label += currentValue + ' prêts (' + percentage + '%)';
                                                return label;
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        // Display message if no data
                        pieChartContainer.innerHTML = '<p class="loading-message">Aucune donnée de livre disponible pour le graphique.</p>';
                    }
                })
                .catch(error => {
                    // Hide loading message and show error
                    loadingMessage.style.display = 'none';
                    errorMessage.style.display = 'block';
                    errorMessage.textContent = `Erreur lors du chargement du graphique: ${error.message}. Veuillez réessayer plus tard.`;
                    console.error('Erreur lors du fetch des données:', error);
                });
        });
    </script>
</body>
</html>