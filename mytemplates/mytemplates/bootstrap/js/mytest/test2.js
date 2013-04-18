$(function () {
    var chart;
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'mycontainer2',
                type: 'column'
            },
            title: {
                text: 'Stacked domu starting time'
            },
            xAxis: {
                categories: ['test_xp', 'test_ubuntu', 'test_centos', 'test_win7', 'test_win8']
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Total time consumption (sec)'
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -100,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        'Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: [{
                name: 'starting time',
                data: [50, 30, 40, 70, 20]
            }, {
                name: 'downloading time',
                data: [20, 20, 30, 20, 10]
            }/*, {
                name: 'Joe',
                data: [3, 4, 4, 2, 5]
            }*/]
        });
    });
    
});