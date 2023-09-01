function drawRevenueStatsWithPrice(labels, data) {
    const ctx = document.getElementById('revenueStats');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                    label: 'Thu',
                    data: data,
                    borderWidth: 1,
                    backgroundColor: ['green']
                },
                {
                    label: 'Chi',
                    data: data2,
                    borderWidth: 1,
                    backgroundColor: ['red']
                }]
        },

        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}