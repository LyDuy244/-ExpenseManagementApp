function delTran(path, id) {
    if (confirm("Bạn chắc chắn xóa giao dịch này chứ?")) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status == 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}


function delGroupTran(path, id) {
    if (confirm("Bạn chắc chắn xóa giao dịch này chứ?")) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status == 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}