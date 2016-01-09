package utils.filter

import javax.inject.Inject

import play.api.http.HttpFilters
import play.filters.csrf.CSRFFilter

/**
 * Created by Sa2 on 15/10/20.
 */
class CustomHttpFilters @Inject()(log: LoggingFilter, csrf: CSRFFilter) extends HttpFilters{

  override val filters = Seq(log, csrf)

}
