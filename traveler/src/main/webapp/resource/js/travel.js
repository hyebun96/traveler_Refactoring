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

function deleteFile(mode, filename, num) {
    if(mode === "update"){
        const url = "/traveler_war_exploded/travel/deleteFile.do?filename=" + filename + "&num=" + num;
        location.href = url;
    }
}

