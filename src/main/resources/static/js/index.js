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

var options = { // Drawing Manager를 생성할 때 사용할 옵션입니다
    map: map, // Drawing Manager로 그리기 요소를 그릴 map 객체입니다
    drawingMode: [ // drawing manager로 제공할 그리기 요소 모드입니다
        kakao.maps.drawing.OverlayType.MARKER,
        kakao.maps.drawing.OverlayType.POLYLINE,
        kakao.maps.drawing.OverlayType.RECTANGLE,
        kakao.maps.drawing.OverlayType.CIRCLE,
        kakao.maps.drawing.OverlayType.POLYGON
    ],
    // 사용자에게 제공할 그리기 가이드 툴팁입니다
    // 사용자에게 도형을 그릴때, 드래그할때, 수정할때 가이드 툴팁을 표시하도록 설정합니다
    guideTooltip: ['draw', 'drag', 'edit'], 
    markerOptions: { // 마커 옵션입니다 
        draggable: true, // 마커를 그리고 나서 드래그 가능하게 합니다 
        removable: true // 마커를 삭제 할 수 있도록 x 버튼이 표시됩니다  
    },
    polylineOptions: { // 선 옵션입니다
        draggable: true, // 그린 후 드래그가 가능하도록 설정합니다
        removable: true, // 그린 후 삭제 할 수 있도록 x 버튼이 표시됩니다
        editable: true, // 그린 후 수정할 수 있도록 설정합니다 
        strokeColor: '#39f', // 선 색
        hintStrokeStyle: 'dash', // 그리중 마우스를 따라다니는 보조선의 선 스타일
        hintStrokeOpacity: 0.5  // 그리중 마우스를 따라다니는 보조선의 투명도
    },
    rectangleOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#39f', // 외곽선 색
        fillColor: '#39f', // 채우기 색
        fillOpacity: 0.5 // 채우기색 투명도
    },
    circleOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#39f',
        fillColor: '#39f',
        fillOpacity: 0.5
    },
    polygonOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#39f',
        fillColor: '#39f',
        fillOpacity: 0.5,
        hintStrokeStyle: 'dash',
        hintStrokeOpacity: 0.5
    }
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
                        alert(response)
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



// 위에 작성한 옵션으로 Drawing Manager를 생성합니다
var manager = new kakao.maps.drawing.DrawingManager(options);

// 버튼 클릭 시 호출되는 핸들러 입니다
function selectOverlay(type) {
    // 그리기 중이면 그리기를 취소합니다
    manager.cancel();

    // 클릭한 그리기 요소 타입을 선택합니다
    manager.select(kakao.maps.drawing.OverlayType[type]);
}

var markerPosition  = new kakao.maps.LatLng(33.450701, 126.570667); 

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

marker.setMap(map);

