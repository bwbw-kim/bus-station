var cur_latitude = 127.0286009;
var cur_longitude = 37.2635727;
if ("geolocation" in navigator) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude, accuracy } = position.coords;
        cur_latitude = latitude;
        cur_longitude = longitude;
      },
      (error) => {
        alert("위치 정보를 가져올수 없습니다.");
      },
      {
        enableHighAccuracy: true,
        timeout: 10000,
        maximumAge: 0
      }
    );
  } else {
    alert("위치 정보를 가져올수 없습니다.");
  }

var mapContainer = document.getElementById('map'),
    mapOption = { 
        center: new kakao.maps.LatLng(cur_longitude, cur_latitude),
        level: 2
    };

var map = new kakao.maps.Map(mapContainer, mapOption); 

var options = {
    map: map,
    drawingMode: [
        kakao.maps.drawing.OverlayType.MARKER,
    ],
    guideTooltip: ['draw', 'drag', 'edit'], 
    markerOptions: { 
        draggable: true,
        removable: true  
    },
};

$('#refresh').click(function() {
    $.ajax({
        url: '/api/range',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            smallLatitude: map.getBounds()["ha"],
            bigLatitude: map.getBounds()["oa"],
            smallLongitude: map.getBounds()["qa"],
            bigLongitude: map.getBounds()["pa"]
        }),
        success: function(response) {
            let init_marker = new kakao.maps.Marker({});
            init_marker.setMap(null);
            busstopMarkerInitialize(response);
        },
        error: function(xhr, status, error) {
            // 요청 실패 시 처리
            console.error('Error:', error);
            alert('Request failed!');
        }
    });
});


function busstopMarkerInitialize(busstopList) {
    for(let busstop of busstopList) {
        let markerPosition  = new kakao.maps.LatLng(busstop["longitude"], busstop["latitude"]); 
        let marker = new kakao.maps.Marker({
            position: markerPosition
        });
        kakao.maps.event.addListener(marker, 'click', function() {
            let tbody = document.getElementById('bus-data');
            tbody.innerHTML = "";
            if (busstop["id"].includes("GGB")) {
                $('#bus-modal').modal();
                $('#bus-modal').modal('open');
                $.ajax({
                    url: '/api/bus-arrival-list',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        stationId : busstop["id"].slice(3)
                    }),
                    success: function(response) {
                        Object.entries(response).forEach(function([busId, busArrivalMap]) {
                            let row = document.createElement('tr');
                            let busNumberTd = document.createElement('td');
                            busNumberTd.textContent = busArrivalMap.busNumber;
                            row.appendChild(busNumberTd);
                            let locationTd= document.createElement('td');
                            locationTd.textContent = busArrivalMap.location;
                            row.appendChild(locationTd);
                            let buttonTd = document.createElement('td');
                            let button = document.createElement('button');
                            button.textContent = '알림 받기';
                            button.classList="waves-effect waves-light btn";
                            button.addEventListener('click', function() {
                                subscribe(busId, busstop["id"].slice(3));
                            });
                            buttonTd.appendChild(button);
                            row.appendChild(buttonTd);
                            tbody.appendChild(row);
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        alert('Request failed!');
                    }
                });
            } else {
                alert("경기 버스만 지원합니다.");
            }
        });
        marker.setMap(map);
    }
}

var manager = new kakao.maps.drawing.DrawingManager(options);

function subscribe (busId, stationId) {
    $.ajax({
        url: '/api/subscribe',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            busId : busId,
            stationId : stationId
        }),
        success: function(response) {
            alert("알림 설정이 완료 되었습니다.");
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('Request failed!');
        }
    });
}


