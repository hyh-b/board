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
        var formData = new FormData();
        formData.append('title', $('#title').val());
        formData.append('content', $('#content').val());
        console.log("검증시작")
        if($('#mfiles')[0].files.length > 0) {
            $.each($('#mfiles')[0].files, function (i, file) {
                formData.append('mfiles', file);
                console.log("파일명" + file);
            });
        }
        
        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            processData: false,  // FormData를 사용할 때 필요
            contentType: false,  // FormData를 사용할 때 필요
            data: formData
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(error) {
            console.log(error);
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