// 댓글 텍스트 클릭 시 대댓글 작성창 토글
document.querySelectorAll('.comment-text').forEach(commentText => {
    commentText.addEventListener('click', () => {
        const replyInput = commentText.closest('.mb-3').querySelector('.reply-input');
        replyInput.style.display = replyInput.style.display === 'block' ? 'none' : 'block';
    });
});

// 대댓글 입력창 클릭 시 이벤트 전파 방지
document.querySelectorAll('.reply-input').forEach(inputArea => {
    inputArea.addEventListener('click', (event) => {
        event.stopPropagation();
    });
});
// 게시글 추천버튼
document.addEventListener('DOMContentLoaded', function () {
    var likeButton = document.getElementById('likeButton');

    likeButton.addEventListener('click', function() {
        if(userSeq == null){
            alert("로그인이 필요합니다")
            return ;
        }else{
            toggleLikeButtonClass(this);
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const commentLikeButton = document.querySelectorAll('.comment-like-button');

    commentLikeButton.forEach(function(button) {
        button.addEventListener('click', function() {
            toggleCommentLikeButtonClass(button);
        });
    });
});

// 댓글달기 버튼
document.getElementById('submitComment').addEventListener('click', function() {
    var commentContent = document.getElementById('commentContent').value;
    if(userSeq != null){
        $.ajax({
            url: '/api/v1/comment/save',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                content: commentContent,
                postSeq: postSeq
            }),
            success: function(response) {
                var newComment = response;

                alert("댓글등록 성공")
                addCommentToView(newComment.user.name,newComment.content,newComment.seq,newComment.like);
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
            }
        });
    }else{
        alert("로그인이 필요합니다")
    }
});

// 댓글등록 후 앳글업데이트
function addCommentToView(userName, content, commentSeq, likeCount) {
    var commentList = document.getElementById("comment-container"); // 댓글 목록의 ID를 가정합니다.

    var commentDiv = document.createElement("div");
    commentDiv.classList.add("mb-3");

    var strongTag = document.createElement("strong");
    strongTag.textContent = userName + ":";
    commentDiv.appendChild(strongTag);

    var spanTag = document.createElement("span");
    spanTag.classList.add("comment-text");
    spanTag.textContent = content;
    commentDiv.appendChild(spanTag);

    var likeButton = document.createElement("button");
    likeButton.classList.add("btn", "btn-outline-info", "comment-like-button");
    likeButton.id = "commentLike" + commentSeq;
    likeButton.textContent = "추천 " + likeCount;
    likeButton.setAttribute('data-seq', commentSeq);
    commentDiv.appendChild(likeButton);

    // 대댓글 입력창과 예시 대댓글을 추가하는 부분도 포함할 수 있습니다.

    commentList.appendChild(commentDiv);
}

function toggleCommentLikeButtonClass(button) {
    var commentSeq = button.getAttribute('data-seq');
    if (button.classList.contains('btn-outline-info')) {
        button.classList.remove('btn-outline-info');
        button.classList.add('btn-info');
        console.log("데이터1: "+commentSeq);
        sendLike(postSeq,"comment",commentSeq);
    } else {
        button.classList.remove('btn-info');
        button.classList.add('btn-outline-info');

    }
}

/*ㅡㅡㅡㅡ 게시글 추천 ㅡㅡㅡㅡ*/
function toggleLikeButtonClass(button) {
    if (button.classList.contains('btn-outline-info')) {
        button.classList.remove('btn-outline-info');
        button.classList.add('btn-info');
        sendLike(postSeq,"post", postSeq)
            .then(() => updateLikesCount(postSeq))
            .catch(error => console.error('Erroe: ',error));
    } else {
        button.classList.remove('btn-info');
        button.classList.add('btn-outline-info');
        deleteLike(postSeq,"post",postSeq)
            .then(() => updateLikesCount(postSeq))
            .catch(error => console.error('Error: ',error));
    }
}

function updateLikesCount(postSeq) {
    $.ajax({
        type: 'GET',
        url: '/api/v1/likes/update/' + postSeq
    }).done(function(response) {
        console.log('업데이트된 추천 수:', response);
        $('#likeButton').text('추천 ' + response);
    }).fail(function(error) {
        console.error('추천 수 업데이트 및 조회 실패', error);
    });
}

function sendLike(postSeq, target, targetSeq) {
    return new Promise((resolve, reject) => {
        console.log("데이터2: "+targetSeq);
        $.ajax({
            type: 'POST',
            url: '/api/v1/likes',
            contentType: 'application/json',
            data: JSON.stringify({
                postSeq: postSeq,
                target: target,
                targetSeq: targetSeq
            }),
            dataType: 'json'
        }).done(function(response) {
            console.log('추천 저장 성공', response);
            resolve(response); // AJAX 요청 성공 시 resolve 호출
        }).fail(function(error) {
            console.log('추천 저장 실패', error);
            reject(error); // AJAX 요청 실패 시 reject 호출
        });
    });
}

function deleteLike(postSeq, target, targetSeq) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/likes/' + postSeq + '/' + target + '/' + targetSeq,
            contentType: 'application/json; charset=utf-8'
        }).done(function(response) {
            console.log("추천취소 성공");
            resolve(response); // AJAX 요청 성공 시 resolve 호출
        }).fail(function(error) {
            console.log("추천취소 에러 발생");
            console.log(error);
            reject(error); // AJAX 요청 실패 시 reject 호출
        });
    });
}

/*ㅡㅡㅡㅡ 수정버튼 ㅡㅡㅡㅡㅡ*/
function updatePost(postUserSeq, userSeq, postSeq) {
    if (userSeq !== null && userSeq == postUserSeq) {
        location.href = '/posts/update/' + postSeq;
    } else {
        alert("권한이 없습니다.");
    }
}

/*ㅡㅡㅡㅡ 삭제버튼 ㅡㅡㅡㅡ*/
function deletePost(postUserSeq, userSeq, postSeq) {
    if (userSeq !== null && userSeq == postUserSeq) {
        if (confirm("정말 삭제하시겠습니까?")) {

            $.ajax({
                type: 'DELETE',
                url: '/api/v1/posts/'+postSeq,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function (){
                alert('글이 삭제되었습니다.');
                window.location.href = '/';
            }).fail(function (error){
                alert(JSON.stringify(error))
            });
        }
    } else {
        alert("권한이 없습니다.");
    }
}