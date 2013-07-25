package w.rdy;

import w.rdy.analysis.ReadabilityMetrics;
import w.rdy.domain.Message;

import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Quick, think of some fancy words!
 */
public class LiteracyRank {

// ContextIo -> AccountId -> Email -> [Message]
public static final Function<ContextIo, Function<String, Function<String, Stream<Message>>>> messages =
    cio -> accountId -> email -> cio.getMessages(accountId, email, true).stream();

public static final Function<Message, Stream<String>> plainText =
    message -> message.body.stream()
        .filter(b -> b.type.equals("text/plain"))
        .map(Message.BodyPart::getContent);

public static final Function<Stream<Message>, Stream<String>> plainTexts =
    messages -> messages.flatMap(plainText);

public static final <T, U, R> Function<Function<U, R>, Function<Function<T, U>, Function<T, R>>> compose() {
  return fn -> fn::compose;
}

public static final Function<Stream<String>, OptionalDouble> avgRank =
    s -> s.map(ReadabilityMetrics.fleschKincaidGradeLevel).mapToDouble(d -> d).average();

// ContextIo -> AccountId -> Email -> Maybe Double
// (((avgRank . plainTexts) .) .) . messages
public static final Function<ContextIo, Function<String, Function<String, OptionalDouble>>> rank =
    // (String -> String -> [Message]) -> (String -> String -> Maybe Double)
    LiteracyRank.<String, Function<String, Stream<Message>>, Function<String, OptionalDouble>>compose().apply(
        // (String -> [Message]) -> (String -> Maybe Double)
        LiteracyRank.<String, Stream<Message>, OptionalDouble>compose().apply(
            // [Message] -> Maybe Double
            avgRank.compose(plainTexts))
    ).compose(messages);
}
