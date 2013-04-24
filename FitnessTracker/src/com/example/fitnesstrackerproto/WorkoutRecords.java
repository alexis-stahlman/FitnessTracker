package com.example.fitnesstrackerproto;

public class WorkoutRecords {
	private int id;
	private String exercise;
	private String duration;
	private String calories;
	private String date;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExercise() {
		return this.exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCalories() {
		return this.calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}
	
	public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return "Date : " + getDate() + "   Duration : " + getDuration();
	}

}