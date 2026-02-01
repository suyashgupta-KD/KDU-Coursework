const postsContainer = document.querySelector('.posts');
const postInput = document.querySelector('.post-input');
const postButton = document.querySelector('.tweet-btn');
const floatingButton = document.querySelector('.floating-tweet-box-icon');
const tweetBox = document.querySelector('.tweet-box');
const tweetClose = document.querySelector('.tweet-close');

const createPostElement = (text) => {
  const article = document.createElement('article');
  article.className = 'post';

  const profileImage = document.createElement('img');
  profileImage.className = 'profile-image';
  profileImage.src = 'icons/profile-pic.png';
  profileImage.alt = 'Profile';

  const postBody = document.createElement('div');
  postBody.className = 'post-body';

  const postHeader = document.createElement('div');
  postHeader.className = 'post-header';

  const nameBlock = document.createElement('div');
  nameBlock.innerHTML = `
    <span class="post-name">Suyash Gupta</span>
    <span class="post-handle">@SUYASHGUPTA0</span>
    <span class="post-time">· now</span>
  `;

  const dots = document.createElement('img');
  dots.className = 'dots';
  dots.src = 'icons/dots-grey.svg';
  dots.alt = 'More';

  postHeader.appendChild(nameBlock);
  postHeader.appendChild(dots);

  const postText = document.createElement('p');
  postText.className = 'post-text';
  postText.textContent = text;

  const events = document.createElement('div');
  events.className = 'post-events';
  events.innerHTML = `
    <button class="post-event comment-event" type="button">
      <img src="icons/comment.svg" alt="Comment" />
      <span class="comments-count">0</span>
    </button>
    <button class="post-event retweet-event" type="button">
      <img class="retweet-icon" src="icons/retweet.svg" alt="Retweet" />
      <span class="retweets-count">0</span>
    </button>
    <button class="post-event like-post" type="button">
      <img class="like-icon" src="icons/like.svg" alt="Like" />
      <span class="likes-count">0</span>
    </button>
    <button class="post-event share-event" type="button">
      <img src="icons/share.svg" alt="Share" />
    </button>
  `;

  const commentForm = document.createElement('form');
  commentForm.className = 'comment-form';
  commentForm.innerHTML = `
    <input class="comment-input" type="text" placeholder="Post your reply" />
    <button class="comment-btn" type="submit">Reply</button>
  `;

  const commentsList = document.createElement('div');
  commentsList.className = 'comments-list';

  postBody.appendChild(postHeader);
  postBody.appendChild(postText);
  postBody.appendChild(events);
  postBody.appendChild(commentForm);
  postBody.appendChild(commentsList);

  article.appendChild(profileImage);
  article.appendChild(postBody);

  return article;
};

const handlePost = () => {
  const text = postInput.value.trim();
  if (!text) {
    postInput.focus();
    return;
  }

  const newPost = createPostElement(text);
  postsContainer.prepend(newPost);
  postInput.value = '';

  if (tweetBox && tweetBox.classList.contains('is-open')) {
    tweetBox.classList.remove('is-open');
  }
};

const toggleLike = (button) => {
  const count = button.querySelector('.likes-count');
  const icon = button.querySelector('.like-icon');
  const isLiked = button.classList.contains('unlike-post');
  let current = parseInt(count.textContent, 10) || 0;

  if (isLiked) {
    button.classList.remove('unlike-post');
    icon.src = 'icons/like.svg';
    current = Math.max(0, current - 1);
  } else {
    button.classList.add('unlike-post');
    icon.src = 'icons/like-pink.svg';
    current += 1;
  }

  count.textContent = current;
};

const toggleRetweet = (button) => {
  const count = button.querySelector('.retweets-count');
  const icon = button.querySelector('.retweet-icon');
  const isActive = button.classList.contains('is-active');
  let current = parseInt(count.textContent, 10) || 0;

  if (isActive) {
    button.classList.remove('is-active');
    icon.src = 'icons/retweet.svg';
    current = Math.max(0, current - 1);
  } else {
    button.classList.add('is-active');
    icon.src = 'icons/retweet-blue.svg';
    current += 1;
  }

  count.textContent = current;
};

const handleCommentSubmit = (form) => {
  const input = form.querySelector('.comment-input');
  const text = input.value.trim();
  if (!text) {
    input.focus();
    return;
  }

  const list = form.nextElementSibling;
  const comment = document.createElement('div');
  comment.className = 'comment';
  comment.textContent = text;
  list.prepend(comment);

  const count = form.parentElement.querySelector('.comments-count');
  const current = parseInt(count.textContent, 10) || 0;
  count.textContent = current + 1;

  input.value = '';
};

postButton.addEventListener('click', handlePost);

postsContainer.addEventListener('click', (event) => {
  const likeButton = event.target.closest('.like-post');
  if (likeButton) {
    toggleLike(likeButton);
    return;
  }

  const retweetButton = event.target.closest('.retweet-event');
  if (retweetButton) {
    toggleRetweet(retweetButton);
  }
});

postsContainer.addEventListener('submit', (event) => {
  if (event.target.matches('.comment-form')) {
    event.preventDefault();
    handleCommentSubmit(event.target);
  }
});

if (floatingButton) {
  floatingButton.addEventListener('click', () => {
    if (tweetBox) {
      tweetBox.classList.add('is-open');
      postInput.focus();
    }
  });
}

if (tweetClose) {
  tweetClose.addEventListener('click', () => {
    if (tweetBox) {
      tweetBox.classList.remove('is-open');
    }
  });
}
