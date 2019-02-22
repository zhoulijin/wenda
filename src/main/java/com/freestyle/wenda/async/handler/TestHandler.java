package com.freestyle.wenda.async.handler;

import com.freestyle.wenda.async.EventHandler;
import com.freestyle.wenda.async.EventModel;
import com.freestyle.wenda.async.EventType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestHandler implements EventHandler {

    @Override
    public void doHandle(EventModel eventModel) {

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return null;
    }
}
