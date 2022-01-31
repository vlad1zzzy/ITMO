const rejected = [];
const stared = [];
const liked = [];
const pigInfos = [
  'John', 'Jacob', 'Michael', 'Joshua', 'Matthew',
  'Ethan', 'Andrew', 'William', 'Daniel', 'Joseph',
  'Christopher', 'Anthony', 'Ryan', 'Nicholas', 'David',
  'Alexander', 'Tyler', 'James', 'Dylan', 'Nathan'
]
const maxPigId = pigInfos.length;
let pigImg,
  pigInfo,
  currentPigId = 0,
  buttons,
  buttonReject,
  buttonStar,
  buttonLike,
  likeList,
  body,
  tinderContainer;

const pigsSource = (id) => {
  const index = String(id).padStart(2, '0');

  return `../assets/img/pig_${index}.jpg`;
};

const nextPig = () => {
  if (currentPigId === maxPigId) {
    pigImg.remove();
    pigInfo.remove();
    buttons.removeChild(buttonReject);
    buttons.removeChild(buttonStar);
    buttons.removeChild(buttonLike);
    const replacer = document.createElement('a');
    replacer.setAttribute('href', 'https://github.com/vlad1zzzy')
    replacer.classList.add('replacer')
    replacer.innerText = 'vlad1zzzy';
    document.querySelector('.card__img').replaceWith(replacer)
    return true;
  }
  pigInfo.innerText = pigInfos[currentPigId];
  pigImg.setAttribute('src', pigsSource(++currentPigId));
}

const makeChoice = (array, like, favorite) => {
  array.unshift(currentPigId);
  like && createLikedItem(favorite);
  nextPig();
}

const addListener = (button, array, like, favorite) => {
  button.addEventListener('click', () => {
    makeChoice(array, like, favorite);
  })
}

const createLikedItem = (favorite) => {
  const element = document.createElement('div');
  element.classList.add('liked__item');
  element.classList.add('burger-menu_link');
  favorite && element.classList.add('favorite');

  const elementImg = document.createElement('img');
  elementImg.setAttribute('src', pigsSource(currentPigId));
  elementImg.setAttribute('alt', 'liked pig');
  elementImg.classList.add('liked__item--inner');
  elementImg.classList.add('inner');

  element.appendChild(elementImg);

  likeList.prepend(element);
}

const addSwiper = () => {
  let t1, t2;
  let touchstartX = 0;
  let touchstartY = 0;
  let touchendX = 0;
  let touchendY = 0;

  const swiper = document.getElementById('swiper');

  const handleTouchStart = (e) => {
    touchstartX = e.changedTouches?.[0].screenX || e.screenX;
    touchstartY = e.changedTouches?.[0].screenY || e.screenY;
  }

  const handleTouchEnd = (e) => {
    touchendX = e.changedTouches?.[0].screenX || e.screenX;
    touchendY = e.changedTouches?.[0].screenY || e.screenY;
    handleSwipe();
  }

  swiper.addEventListener('touchstart', handleTouchStart, false);
  swiper.addEventListener('touchend', handleTouchEnd, false);

  swiper.addEventListener('mousedown', handleTouchStart, false);
  swiper.addEventListener('mouseup', handleTouchEnd, false);

  const handleSwipe = () => {
    if (touchendX <= touchstartX - 5) {
      clearTimeout(t1);
      buttonReject.style.transform = 'scale(1.2)';
      makeChoice(rejected);
      t1 = setTimeout(() => {
        buttonReject.style.transform = 'scale(1)';
      }, 200);
    }

    if (touchendX >= touchstartX + 5) {
      clearTimeout(t2);
      buttonLike.style.transform = 'scale(1.2)';
      makeChoice(liked, true);
      t2 = setTimeout(() => {
        buttonLike.style.transform = 'scale(1)';
      }, 200);
    }
  }
}

const burgerMenu = () => {
  const menu = document.querySelector('.burger-menu');
  const button = document.querySelector('.burger-menu_button');
  const overlay = document.querySelector('.burger-menu_overlay');

  button.addEventListener('click', (e) => {
    e.preventDefault();
    toggleMenu();
  });

  overlay.addEventListener('click', () => toggleMenu());

  function toggleMenu() {
    menu.classList.toggle('burger-menu_active');

    if (menu.classList.contains('burger-menu_active')) {
      body.style.overflow = 'hidden';
    } else {
      body.style.overflow = 'visible';
    }
  }
}

const addAnimation = (el) => {
  const hammertime = new Hammer(el);

  hammertime.on('pan', function () {
    el.classList.add('moving');
  });

  hammertime.on('pan', function (event) {
    if (event.deltaX === 0) return;
    if (event.center.x === 0 && event.center.y === 0) return;

    tinderContainer.classList.toggle('tinder_love', event.deltaX > 0);
    tinderContainer.classList.toggle('tinder_nope', event.deltaX < 0);

    const xMulti = event.deltaX * 0.03;
    const yMulti = event.deltaY / 80;
    const rotate = xMulti * yMulti;

    event.target.style.transform = 'translate(' + event.deltaX + 'px, ' + event.deltaY + 'px) rotate(' + rotate + 'deg)';
  });

  hammertime.on('panend', function () {
    el.classList.remove('removed');
    el.style.transform = "none";
    tinderContainer.classList.add('loaded');
  });
}

window.onload = () => {
  body = document.querySelector('body');
  tinderContainer = document.querySelector('.tinder');
  pigImg = document.querySelector('.card__img-inner');
  pigInfo = document.querySelector('.card__img-info');
  nextPig()

  buttons = document.querySelector('.card__buttons');
  [buttonReject, buttonStar, buttonLike] = buttons.children;

  addListener(buttonReject, rejected);
  addListener(buttonStar, stared, true, true);
  addListener(buttonLike, liked, true);

  likeList = document.querySelector('.liked');

  addSwiper();
  burgerMenu();
  addAnimation(document.querySelector('.tinder--card'));
}
