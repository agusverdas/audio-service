var audios = $('audio');
audios.bind('timeupdate', function () {
    if (this.currentTime >= 30) this.pause();
});