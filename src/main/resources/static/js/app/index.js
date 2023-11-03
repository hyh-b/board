var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click',function (){   //btn-save버튼 클릭 시 save()메서드 실행
            _this.save();
        });

        $('#btn-update').on('click',function (){   //btn-update버튼 클릭 시 update()메서드 실행
            _this.update();
        });

        $('#btn-delete').on('click',function (){   //btn-delete버튼 클릭 시 delete()메서드 실행
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',   //REST에서 CRUD는 다음과 같이 HTTP Method에 매핑 - 생성:POST, 읽기:GET, 수정:PUT, 삭제:DELETE
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (){   //성공시 수행할 메소드
            alert('글이 등록되었습니다.');
            window.location.href = '/';   //알림창을 띄운 뒤 '/'경로로 이동
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',   //PostApiController에 있는 API에서 이미 @PutMapping로 선언했기에 PUT 사용
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (){
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error))
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function (){
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error){
            alert(JSON.stringify(error))
        });
    }
};

main.init();