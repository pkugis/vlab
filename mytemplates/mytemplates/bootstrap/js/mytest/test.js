$(function () {
    var chart;
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'mycontainer',
                type: 'line'
            },
            title: {
                text: 'Real time overhead of each host'
            },
            /*subtitle: {
                text: 'Source: WorldClimate.com'
            },*/
            xAxis: {
                categories: ['001', '002', '003', '004', '005', '006', '007', '008', '009', '010', '011', '012']
            },
            yAxis: {
                title: {
                    text: 'overhead ratio (%)'
                }
            },
            tooltip: {
                enabled: false,
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+
                        this.x +': '+ this.y +'°C';
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
            series: [{
                name: 'CPU overhead',
                data: [70, 69, 95, 145, 184, 215, 252, 265, 233, 183, 139, 36]
            }, {
                name: 'Memory overhead',
                data: [39, 42, 57, 85, 89, 72, 85, 74, 71, 23, 16, 48]
            }]
        });
    });
    
});