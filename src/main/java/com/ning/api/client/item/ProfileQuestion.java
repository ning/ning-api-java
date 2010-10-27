package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Value class used for return value of {@link UserField#profileQuestions} property,
 * accessed using {@link User#getProfileQuestions()}.
 *<p>
 * NOTE: result JSON that Bazel returns is constructed in an interesting way;
 * mapping is done based on observing structure, using best guestimations for
 * intended semantics.
 */
public class ProfileQuestion
{
    // JSON has this as 'question' property, although it really looks more like an id of some sort, so:
    @JsonProperty("question") protected String id;
    // And Answer is actual JSON object:
    @JsonProperty protected Answer answer;

    public ProfileQuestion() { }

    /**
     * Returns question identifier
     */
    public String getId() { return id; }

    public String getQuestion() { return (answer == null) ? null : answer.question; }
    public String getAnswer() { return (answer == null) ? null : answer.answer; }
    public String getType()  { return (answer == null) ? null : answer.type; }
    public String getChoices()  { return (answer == null) ? null : answer.type; }
    public boolean isPrivate()  { return (answer == null) ? false : answer.isPrivate; }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("[ProfileQuestion ").append(getId())
            .append(": ").append(answer)
            .toString();
    }
    
    /**
     * Bazel returns answers as separate objects, using this structure
     */
    protected final static class Answer {
        public String question; // question String
        public String type; // "select" etc
        public String choices; // "a,b" etc
        @JsonProperty("private") public boolean isPrivate;
        public String answer; // User's answer

        @Override
        public String toString() {
            return new StringBuilder()
                .append("[question=").append(question)
                .append(", type=").append(type)
                .append(", choices=").append(choices)
                .append(", private=").append(isPrivate)
                .append(", answer=").append(answer)
                .append(']')
                .toString();
        }
    }
}
