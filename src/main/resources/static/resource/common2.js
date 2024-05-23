$('select[data-value]').each(function(index, el) {
	const $el = $(el);

	defaultValue = $el.attr('data-value').trim();

	if (defaultValue.length > 0) {
		$el.val(defaultValue);
	}
});

//-----------------------------------------------------------------메뉴보기 숨기기꺼
function toggleSideMenu() {
  const sideMenu = $('.side-menu');
  sideMenu.toggleClass('appear');

  if (sideMenu.hasClass('appear')) {
    sideMenu.css('left', '0');
    sideMenu.css('transition', 'left 0.5s');
    $('.menu-button').text('메뉴 숨기기');
  } else {
    sideMenu.css('left', '-300px');
    sideMenu.css('transition', 'left 0.5s');
    $('.menu-button').text('메뉴 보기');
  }
}

// 이벤트 리스너 추가
$('.menu-button').on('click', toggleSideMenu);






