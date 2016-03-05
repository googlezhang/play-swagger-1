package nested_arrays_validation
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
type ExampleNestedArraysOptArr = ArrayWrapper[ExampleNestedArraysOptArrArr]

    type ExampleNestedArraysOpt = ArrayWrapper[ExampleNestedArraysOptArr]

    type ExampleMessagesOpt = ArrayWrapper[ExampleMessagesOptArr]

    type ExampleMessages = Option[ExampleMessagesOpt]

    type ExampleMessagesOptArr = ArrayWrapper[Activity]

    type GetExample = Option[Example]

    type ExampleNestedArraysOptArrArr = ArrayWrapper[ExampleNestedArraysOptArrArrArr]

    type ExampleNestedArrays = Option[ExampleNestedArraysOpt]

    type ExampleNestedArraysOptArrArrArr = ArrayWrapper[String]

    type ActivityActions = Option[String]

    type GetResponses200 = Null

    case class Activity(actions: ActivityActions) 

    case class Example(messages: ExampleMessages, nestedArrays: ExampleNestedArrays) 

    


    
    
    }