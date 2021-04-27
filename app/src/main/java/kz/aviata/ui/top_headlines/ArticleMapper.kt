package kz.aviata.ui.top_headlines

import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.aviata.ui.top_headlines.dvo.ArticleDvo
import kz.aviata.ui.top_headlines.dvo.DateDvo
import kz.aviata.ui.top_headlines.dvo.SourceDvo
import kz.aviata.utils.DateUtils
import kz.aviata.utils.formatWithPattern
import kz.butik.domain.entities.Article
import kz.butik.domain.entities.Source
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ArticleMapper {

    fun mapToDvo(articles: List<Article>): List<ArticleAdapterItem> {
        val mappedArticles = mutableListOf<ArticleAdapterItem>()
        var lastDate: LocalDate? = null

        articles.forEach { article ->
            val operationDateTime = LocalDateTime
                .parse(article.publishedAt, DateTimeFormatter.ISO_DATE_TIME)

            val articleItem = ArticleDvo(
               mapToSourceDvo(article.source),
                article.author,
                article.title,
                article.description,
                article.url,
                article.urlToImage,
                article.publishedAt,
                article.content,
                isFav = article.isFavorite
            )

            val operationDate = operationDateTime.toLocalDate()

            if (lastDate == null || lastDate?.isEqual(operationDate) == false) {
                mappedArticles.add(getDayItem(operationDate))
                lastDate = operationDate
            }
            mappedArticles.add(articleItem)
        }
        return mappedArticles
    }

    private fun mapToSourceDvo(source: Source): SourceDvo {
        return SourceDvo(
            source.id,
            source.name
        )
    }

    private fun getDayItem(localDate: LocalDate): DateDvo {
        return when {
            LocalDate.now().isEqual(localDate) -> {
                DateDvo("today")
            }
            LocalDate.now().minusDays(1).isEqual(localDate) -> {
                DateDvo("yesterday")
            }
            else -> {
                DateDvo(localDate.formatWithPattern(DateUtils.ddMMyyyy))
            }
        }
    }
}