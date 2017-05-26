package com.berko.call.logger.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.berko.call.logger.domain.enumeration.Direction;

/**
 * A CallLog.
 */

@Document(collection = "call_log")
public class CallLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("phone_number")
    private String phoneNumber;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("transcript")
    private String transcript;

    @Field("recording")
    private String recording;

    @Field("direction")
    private Direction direction;

    @Field("notes")
    private String notes;

    @Field("start_time")
    private ZonedDateTime startTime;

    @Field("end_time")
    private ZonedDateTime endTime;

    @Field("call_back")
    private ZonedDateTime callBack;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CallLog phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public CallLog firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CallLog lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTranscript() {
        return transcript;
    }

    public CallLog transcript(String transcript) {
        this.transcript = transcript;
        return this;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getRecording() {
        return recording;
    }

    public CallLog recording(String recording) {
        this.recording = recording;
        return this;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public Direction getDirection() {
        return direction;
    }

    public CallLog direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getNotes() {
        return notes;
    }

    public CallLog notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public CallLog startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public CallLog endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public ZonedDateTime getCallBack() {
        return callBack;
    }

    public CallLog callBack(ZonedDateTime callBack) {
        this.callBack = callBack;
        return this;
    }

    public void setCallBack(ZonedDateTime callBack) {
        this.callBack = callBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CallLog callLog = (CallLog) o;
        if (callLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), callLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CallLog{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", transcript='" + getTranscript() + "'" +
            ", recording='" + getRecording() + "'" +
            ", direction='" + getDirection() + "'" +
            ", notes='" + getNotes() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", callBack='" + getCallBack() + "'" +
            "}";
    }
}
