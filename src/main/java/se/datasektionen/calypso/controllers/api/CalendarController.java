package se.datasektionen.calypso.controllers.api;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.datasektionen.calypso.models.repositories.ApiRepository;

import static se.datasektionen.calypso.util.DateUtils.ldtToDate;

@Controller
public class CalendarController {

	private ApiRepository apiRepository;

	@Autowired
	public CalendarController(ApiRepository apiRepository) {
		this.apiRepository = apiRepository;
	}

	@RequestMapping(produces = "text/calendar", method = RequestMethod.GET, value = "/feeds/ical")
	@ResponseBody
	public String eventFeed() {
		ICalendar ical = new ICalendar();
		ical.setName("Datasektionen");
		ical.setColor(new Color("hotpink"));
		ical.setProductId("-//Datasektionen//Calypso//SV");

		apiRepository
				.allEvents()
				.stream()
				.map(e -> {
					VEvent event = new VEvent();
					event.setUid(e.getId().toString());
					event.setSummary(e.getTitleSwedish());
					event.setDescription(e.getContentSwedish());
					event.setDateStart(ldtToDate(e.getEventStartTime()));
					event.setDateEnd(ldtToDate(e.getEventEndTime()));
					return event;
				})
				.forEach(ical::addEvent);

		return Biweekly.write(ical).go();
	}

}
