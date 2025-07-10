<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statistiques sur les livres</title>
</head>
<body>
    <!-- Fixed script tag with proper syntax and correct Chart.js URL -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

    <div class="container" sytle="display:flex">
        <div class="left">
            <canvas id="myChart" style="width:100%;max-width:200px"></canvas>
        </div>
        <div class="right">
            <h2>Votre tarte en 1 click</h2>
    </div>
    </div>    
    
    <script>
        // Wait for Chart.js to load before executing
        document.addEventListener('DOMContentLoaded', function() {
            const ctx = document.getElementById('myChart').getContext('2d');
            
            var xValues = ["Le Petit Prince", "Jamais Plus", "One Piece"];
            var yValues = [100, 24, 344];
            var barColors = [
                "#b91d47",
                "#b93a47",
                "#331d45"
            ];
            
            new Chart(ctx, {
                type: "pie",
                data: {
                    labels: xValues,
                    datasets: [{
                        backgroundColor: barColors,
                        data: yValues
                    }]
                },
                options: {
                    plugins: {
                        title: {
                            display: true,
                            text: "Livres les plus empruntes"
                        }
                    }
                }
            });
        });
    </script>
</body>
</html>