// travel.js

function createTravel(mode, num) {
    const f = document.travelForm;

    let str = f.place.value;
    if (!str) {
        alert("여행지 장소를 입력하세요.");
        f.place.focus();
        return;
    }

    str = f.information.value;
    if (!str) {
        alert("여행지 정보를 입력하세요.");
        f.information.focus();
        return;
    }

    f.action = "/traveler_war_exploded/travel/" + mode + "_ok.do?num=" + num;
    f.submit();
}

function updateTravel(num, type){
    const url = '/traveler_war_exploded/travel/update.do?num=' + num + '&type=' + type;

    if (confirm("자료를 수정 하시겠습니까 ? ")){
        location.href = url;
    }
}

function deleteTravel(num, type) {
    const url = '/traveler_war_exploded/travel/delete.do?num=' + num + '&type=' + type;

    if (confirm("자료를 삭제 하시 겠습니까 ? ")){
        location.href = url;
    }
}

function deleteFile(mode, filename, num) {
    if(mode === "update"){
        if (confirm("파일을 삭제 하시겠습니까?")) {
            const url = "/traveler_war_exploded/travel/deleteFile.do?filename=" + filename + "&num=" + num;
            location.href = url;
        }
    }
}

