package hera.api.schemas

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class Test : Query {
	fun test(): String {
		return "test successfull!"
	}
}