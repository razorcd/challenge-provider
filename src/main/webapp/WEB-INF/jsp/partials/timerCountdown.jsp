<div style="font-size: 2em;"><strong id="timer"></strong></div>

<script>
    // TIMER COUNTDOWN
    $(document).ready(function() {
        var startTimeMin = Date.parse("${challenge.challengeSource.startedTimestamp}") / 1000 / 60;
        var durationMin = ${challenge.challengeSource.allowedDurationMin};
        var el = $("#timer");

        interval = setInterval(function() {
            timeLeft = parseInt(durationMin - ((Date.now()/60/1000) - startTimeMin));
            if (timeLeft <= 0) {
                el.text("Timer ended.");
                window.clearInterval(interval);
            } else {
                el.text("Time left: " + moment.duration(timeLeft, 'minutes').humanize());
            }
        }, 1000)
    });
</script>