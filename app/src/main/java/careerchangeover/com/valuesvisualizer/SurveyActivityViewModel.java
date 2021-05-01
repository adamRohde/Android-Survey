package careerchangeover.com.valuesvisualizer;

import androidx.lifecycle.ViewModel;

public class SurveyActivityViewModel extends ViewModel {
    private int questionID;
    private boolean hasBeenAnswered;
    private boolean isCurrentlyExpanded;
    private String question;
    private String dimension;
    private int selfRank;
    private int employerRank;

    public SurveyActivityViewModel() {}

    public void setQuestionID(int _questionNum){
        questionID = _questionNum;
    }
    public int getQuestionID(){
        return questionID;
    }

    public void setHasBeenAnswered(boolean _hasBeenAnswered){
        hasBeenAnswered = _hasBeenAnswered;
    }
    public boolean getHasBeenAnswered(){
        return hasBeenAnswered;
    }

    public void setIsCurrentlyExpanded(boolean _isCurrentlyExpanded){
        isCurrentlyExpanded = _isCurrentlyExpanded;
    }
    public boolean getIsCurrentlyExpanded(){
        return isCurrentlyExpanded;
    }

    public void setQuestion(String _question){
        question = _question;
    }
    public String getQuestion(){
        return question;
    }

    public void setDimension(String _dimension){
        dimension = _dimension;
    }
    public String getDimension(){
        return dimension;
    }

    public void setSelfRank(int _selfRank){
        selfRank = _selfRank;
    }
    public int getSelfRank(){
        return selfRank;
    }

    public void setEmployerRank(int _employerRank){
        employerRank = _employerRank;
    }
    public int getEmployerRank(){
        return employerRank;
    }
}
